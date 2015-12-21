package com.oreumio.james.rest.send;

import com.oreumio.james.core.MimeMessageInputStream;
import com.oreumio.james.index.MessageIndexer;
import com.oreumio.james.rest.AppException;
import com.oreumio.james.rest.util.EmlCommonConstants;
import com.oreumio.james.rest.util.EmlMailUtil;
import com.oreumio.james.rest.util.FileUtil;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.james.mailbox.MailboxManager;
import org.apache.james.mailbox.MailboxSession;
import org.apache.james.mailbox.MessageManager;
import org.apache.james.mailbox.exception.MailboxException;
import org.apache.james.mailbox.exception.MailboxExistsException;
import org.apache.james.mailbox.exception.MailboxNotFoundException;
import org.apache.james.mailbox.model.*;
import org.apache.james.mailbox.model.SearchQuery.Criterion;
import org.apache.james.mailbox.model.SearchQuery.DateResolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 * 메일함에 관련된 모든 작업 처리
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Component
public class EmlMailboxUtilService {

	static final Logger LOGGER = LoggerFactory.getLogger("james.webmail");

	@Autowired
	private MailboxManager mailboxmanager;

	@Autowired
	private MessageSource messageSource;

	/**
	 * @param mailboxmanager
	 */
	public void setMailboxmanager(MailboxManager mailboxmanager) {
		this.mailboxmanager = mailboxmanager;
	}

	/**
	 * 메일함 로그인 처리
	 * @param userId 이메일 아이디(EML_USER 테이블 PK)
	 * @return 로그인 세션 정보
	 * @throws org.apache.james.mailbox.exception.MailboxException e1
	 */
	public MailboxSession loginMailboxByUserId(String userId) throws MailboxException {
		return loginMailboxByUserId(userId, false);
	}

	/**
	 * 메일함 로그인 처리
	 * @param userId 이메일 아이디(EML_USER 테이블 PK)
	 * @param noLabeling 메일함에 저장할 때 라벨 자동 적용 안함 여부
	 * @return 로그인 세션 정보
	 * @throws org.apache.james.mailbox.exception.MailboxException e1
	 */
	public MailboxSession loginMailboxByUserId(String userId, boolean noLabeling) throws MailboxException {
		String[] mailboxNames = new String[] {EmlCommonConstants.RECV_BOX, EmlCommonConstants.TRASH_BOX,
				EmlCommonConstants.SPAM_BOX, EmlCommonConstants.SENT_BOX, EmlCommonConstants.DRAFT_BOX,
				EmlCommonConstants.RESERVED_BOX};
		MailboxSession mailboxSession = null;
		mailboxSession = mailboxmanager.createSystemSession(userId, new HashMap<String, Object>(), LOGGER);

		if (noLabeling) {
			mailboxSession.getAttributes().put("com.oreumio.kill_labeling", "Y");
		}

        // 마임 취득 시, 웹버그 첨가 금지
        mailboxSession.getAttributes().put("com.oreumio.bugged", Boolean.TRUE);

		String userKey = mailboxSession.getUser().getUserName();
		for (String mailboxName : mailboxNames) {
		    MailboxPath mailboxPath = new MailboxPath(MailboxConstants.USER_NAMESPACE, userKey, mailboxName);
		    if (!mailboxmanager.mailboxExists(mailboxPath, mailboxSession)) {
		        try {
		        	mailboxSession.getLog().debug(mailboxName + " does not exist. Creating it.");
		        	mailboxmanager.createMailbox(mailboxPath, mailboxSession);
		        } catch (MailboxExistsException e) {
		            if (mailboxSession.getLog().isDebugEnabled()) {
		            	mailboxSession.getLog().debug("Mailbox created by concurrent call. "
		            			+ "Safe to ignore this exception.");
		            }
		        }
		    }
		}
		return mailboxSession;
	}

	/**
	 * 메일함을 생성한다.
	 * @param userId 생성자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param mailboxFullName 생성할 메일함 전체 경로
	 * @return 성공 여부
	 * @throws org.apache.james.mailbox.exception.MailboxException e
	 */
	public boolean createMailbox(String userId, String mailboxFullName)
			throws MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath MailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, mailboxFullName);
			//중복 체크
			if (mailboxmanager.mailboxExists(MailboxPath, session)) {
				return false;
			}
			mailboxmanager.createMailbox(MailboxPath, session);
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	                mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	                e.printStackTrace();
	             }
	         }
		}
		return true;
	}

	/**
	 * 메일함명을 변경한다.
	 * @param userId 생성자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 원래 메일함 경로
	 * @param renamefolderFullName 변경할 메일함 경로
	 * @return 성공 여부
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public boolean renameMailbox(String userId, String folderFullName, String renamefolderFullName)
			throws MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath oldMailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			MailboxPath newMailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, renamefolderFullName);
			//중복 체크
			if (mailboxmanager.mailboxExists(newMailboxPath, session)) {
				return false;
			}
			mailboxmanager.renameMailbox(oldMailboxPath, newMailboxPath, session);
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
		return true;
	}

	/**
	 * 메일함을 삭제한다.
	 * @param userId 생성자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 원래 메일함 경로
	 * @return 성공 여부
	 */
	public boolean deleteMailbox(String userId, String folderFullName) {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			mailboxmanager.deleteMailbox(mailboxPath, session);
		} catch (MailboxException e) {
			e.printStackTrace();
			return false;
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
		return true;
	}

	/**
	 * 메일함에 여러개의 Message 저장한다.
	 * @param userId 생성자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 원래 메일함 경로
	 * @param msgs 메일 Message 목록
	 * @param noLabeling 라벨 자동 적용 안함 여부
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void appendMessage(String userId, String folderFullName, MimeMessage[] msgs, boolean noLabeling)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId, noLabeling);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			MessageManager messageManager = mailboxmanager.getMailbox(mailboxPath, session);
			for (MimeMessage msg : msgs) {
				Date internalDate = new Date();
				if (msg.getReceivedDate() != null) { //메일 업로드할 때는 Date 헤더값을 receivedDate에 셋팅함
					internalDate = msg.getReceivedDate();
				}
				messageManager.appendMessage(new MimeMessageInputStream(msg), internalDate, session, true,
						msg.getFlags());
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 메일함에 여러개의 Message 저장한다.
	 * @param userId 생성자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 원래 메일함 경로
	 * @param msgs 메일 Message 목록
	 * @param fileName 파일 이름 목록
	 * @param noLabeling 라벨 자동 적용 안함 여부
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void appendMessage(String userId, String folderFullName, MimeMessage[] msgs,
			String[] fileName, boolean noLabeling) throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId, noLabeling);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			MessageManager messageManager = mailboxmanager.getMailbox(mailboxPath, session);
			int i = 0;
			for (MimeMessage msg : msgs) {
				if (fileName != null && fileName.length > 0) {
					LOGGER.info("파일명:" + fileName[i]);
				}
				Date internalDate = new Date();
				if (msg.getReceivedDate() != null) { //메일 업로드할 때는 Date 헤더값을 receivedDate에 셋팅함
					internalDate = msg.getReceivedDate();
				}
				messageManager.appendMessage(new MimeMessageInputStream(msg), internalDate, session, true,
						msg.getFlags());
				i++;
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 테스트 메일 입력
	 * @param userId 생성자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 원래 메일함 경로
	 * @param eml 메일 MIME 내용
	 * @param cnt 입력 횟수
	 */
	public void appendMessage(String userId, String folderFullName, String eml, int cnt) throws MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			MessageManager messageManager = mailboxmanager.getMailbox(mailboxPath, session);
			for (int i = 0; i < cnt; i++) {
				messageManager.appendMessage(new ByteArrayInputStream(eml.getBytes()), new Date(), session, true,
						new Flags());
			}
        } finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 메일함에 Mime Message를 저장한다.
	 * @param userId 생성자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 원래 메일함 경로
	 * @param eml 메일 MIME 내용
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void appendMessage(String userId, String folderFullName, String eml)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			MessageManager messageManager = mailboxmanager.getMailbox(mailboxPath, session);
			messageManager.appendMessage(new ByteArrayInputStream(eml.getBytes()), new Date(), session, true,
					new Flags());
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 메일함에 여러개의 Message 이동한다.
	 * @param userId 생성자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 원래 메일함 경로
	 * @param destFolderFullName 복사될 메일함 경로
	 * @param uids 메일 UID 목록
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void moveMessages(String userId, String folderFullName, String destFolderFullName, List<Long> uids)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			MailboxPath destMailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, destFolderFullName);

			List<MessageRange> ranges = MessageRange.toRanges(uids);
			for (MessageRange range : ranges) {
				mailboxmanager.copyMessages(range, mailboxPath, destMailboxPath, session);
            }
			MessageManager messageManager = mailboxmanager.getMailbox(mailboxPath, session);
			expungeWithoutBackup(messageManager, session, ranges); //백업안남기고 완전 삭제
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 메일함에 여러개의 Message 이동한다.
	 * @param userId 생성자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 이동할 메일함 경로
	 * @param multiMap 복사될 메일함 경로와 메일 UID 목록
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void moveMessages(String userId, String folderFullName, MultiValueMap multiMap)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			//이동될 메일함
			MailboxPath destMailboxPath = new MailboxPath(session.getPersonalSpace(), userKey,
					folderFullName);
			for (Object mailInfo : multiMap.keySet()) {
				String mailboxPath = mailInfo.toString();
				if (mailboxPath.equals(folderFullName)) { //옮길 메일함과 현재 메일함이 같으면 처리하지 않는다.
					continue;
				}
				//기존 메일함
				MailboxPath existMailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, mailboxPath);

				//이동할 메일의 UID 목록
				List<MessageRange> ranges = MessageRange.toRanges((List<Long>) multiMap.get(mailboxPath));
				for (MessageRange range : ranges) {
					try {
						mailboxmanager.copyMessages(range, existMailboxPath, destMailboxPath, session);
					} catch (MailboxNotFoundException e) {
						/*"메일함이 존재하지 않습니다."*/
						throw new AppException(messageSource.getMessage("mail.desc.notExistMailboxPlz", null, "", LocaleContextHolder.getLocale()));
					}
	            }
				MessageManager messageManager = mailboxmanager.getMailbox(existMailboxPath, session);
				expungeWithoutBackup(messageManager, session, ranges); //백업안남기고 완전 삭제
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 여러개의 메일을 삭제한다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param multiMap 복사될 메일함 경로와 메일 UID 목록
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void expunge(String userId, MultiValueMap multiMap)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();

			for (Object mailInfo : multiMap.keySet()) {
				String mailboxPath = mailInfo.toString(); //메일함 경로
				//기존 메일함
				MailboxPath existMailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, mailboxPath);
				try {
					MessageManager messageManager = mailboxmanager.getMailbox(existMailboxPath, session);
					//이동할 메일의 UID 목록
					List<MessageRange> ranges = MessageRange.toRanges((List<Long>) multiMap.get(mailboxPath));
					for (MessageRange range : ranges) {
						messageManager.setFlags(new Flags(Flag.DELETED), MessageManager.FlagEditMode.ADD, range, session);
						messageManager.expunge(range, session);
					}
				} catch (MailboxNotFoundException e) { //메일함이 없으면 무시
					e.printStackTrace();
				}
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 여러개의 메일을 백업 안남기고 삭제한다.
	 * @param userId 로그인 이메일
	 * @param multiMap 메일함 경로와 uid 목록
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void expungeWithoutBackup(String userId, MultiValueMap multiMap) throws IOException,
		MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();

			for (Object mailInfo : multiMap.keySet()) {
				String mailboxPath = mailInfo.toString();
				//기존 메일함
				MailboxPath existMailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, mailboxPath);
				MessageManager messageManager = mailboxmanager.getMailbox(existMailboxPath, session);
				List<MessageRange> ranges = MessageRange.toRanges((List<Long>) multiMap.get(mailboxPath));
				expungeWithoutBackup(messageManager, session, ranges); //백업안남기고 완전 삭제
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 여러개의 메일을 백업 안남기고 삭제한다.
	 * @param messageManager 메세지 처리 매니져
	 * @param mailboxSession 메일함 세션
	 * @param ranges 메일 UID 범위
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void expungeWithoutBackup(MessageManager messageManager, MailboxSession mailboxSession,
			List<MessageRange> ranges) throws IOException, MessagingException, MailboxException {
		mailboxSession.getAttributes().put("com.newriseup.expunge", "Y"); //백업 안남김
		for (MessageRange range : ranges) {
			messageManager.setFlags(new Flags(Flag.DELETED), MessageManager.FlagEditMode.ADD, range, mailboxSession);
			messageManager.expunge(range, mailboxSession);
		}
		mailboxSession.getAttributes().remove("com.newriseup.expunge"); //백업 안남김 설정 제거
	}

	/**
	 * 메일함을 비운다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 메일함 경로
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void expungeMailbox(String userId, String folderFullName)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			try {
				MessageManager messageManager = mailboxmanager.getMailbox(mailboxPath, session);
				messageManager.setFlags(new Flags(Flag.DELETED), MessageManager.FlagEditMode.ADD, MessageRange.all(), session);
				messageManager.expunge(MessageRange.all(), session);
			} catch (MailboxNotFoundException e) { //메일함이 없으면 무시
				e.printStackTrace();
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 기간으로 검색해서 메일 삭제한다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 메일함 경로
	 * @param startDt 시작일
	 * @param endDt 종료일
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 * @throws java.text.ParseException
	 */
	public void expungeByPeriod(String userId, String folderFullName, String startDt, String endDt)
			throws IOException, MessagingException, MailboxException, ParseException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			try {
				MessageManager messageManager = mailboxmanager.getMailbox(mailboxPath, session);
				SearchQuery searchQuery = new SearchQuery();
				SimpleDateFormat startFormatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
				SimpleDateFormat endFormatter = new SimpleDateFormat("yyyyMMddHHmmssZ"); //+0900 정보 포함
				Date start = startFormatter.parse(startDt);
				Date end = endFormatter.parse(endDt);

				Criterion startCriterion = SearchQuery.or(SearchQuery.internalDateOn(start, DateResolution.Day),
						SearchQuery.internalDateAfter(start, DateResolution.Day));
				Criterion endCriterion = SearchQuery.or(SearchQuery.internalDateOn(end, DateResolution.Day),
						SearchQuery.internalDateBefore(end, DateResolution.Day));
				searchQuery.andCriteria(startCriterion);
				searchQuery.andCriteria(endCriterion);

				Iterator<Long> searchList = messageManager.search(searchQuery, session);
				while (searchList.hasNext()) {
					Long uid = searchList.next();
					messageManager.setFlags(new Flags(Flag.DELETED), MessageManager.FlagEditMode.ADD, MessageRange.one(uid), session);
					messageManager.expunge(MessageRange.one(uid), session);
				}
			} catch (MailboxNotFoundException e) { //메일함이 없으면 무시
				e.printStackTrace();
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 기간으로 검색해서 메일 목록을 구한다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 메일함 경로
	 * @param startDt 시작일
	 * @param endDt 종료일
	 * @param path 압축파일 생성 경로
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 * @throws java.text.ParseException
	 * @return 메일 개수
	 */
	public String backupMailByPeriod(String userId, String folderFullName, String startDt, String endDt, String path)
			throws IOException, MessagingException, MailboxException, ParseException {
		MailboxSession session = null;
		int i = 0;
		String title = "";
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			MessageManager messageManager = null;
			try {
				messageManager = mailboxmanager.getMailbox(mailboxPath, session);
			} catch (MailboxNotFoundException e) { //메일함이 없으면 무시
				/*"메일함이 존재하지 않습니다."*/
				throw new AppException(messageSource.getMessage("mail.desc.notExistMailboxPlz", null, "", LocaleContextHolder.getLocale()));
			}
			SearchQuery searchQuery = new SearchQuery();
			SimpleDateFormat startFormatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
			SimpleDateFormat endFormatter = new SimpleDateFormat("yyyyMMddHHmmssZ"); //+0900 정보 포함
			Date start = startFormatter.parse(startDt);
			Date end = endFormatter.parse(endDt);

			Criterion startCriterion = SearchQuery.or(SearchQuery.internalDateOn(start, DateResolution.Day),
					SearchQuery.internalDateAfter(start, DateResolution.Day));
			Criterion endCriterion = SearchQuery.or(SearchQuery.internalDateOn(end, DateResolution.Day),
					SearchQuery.internalDateBefore(end, DateResolution.Day));
			searchQuery.andCriteria(startCriterion);
			searchQuery.andCriteria(endCriterion);

			Iterator<Long> searchList = messageManager.search(searchQuery, session);
			Session mailSession = EmlMailUtil.getMailSession();
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String sentDate = "";
			while (searchList.hasNext()) {
				MessageResultIterator iter = messageManager.getMessages(MessageRange.one(searchList.next()),
						FetchGroupImpl.FULL_CONTENT, session);
				while (iter.hasNext()) {
					MessageResult result = iter.next();
					//Gets the full message including headers and body.
					//The message data should have normalised line endings (CRLF).
					Content body = result.getFullContent();
					InputStream in = null;
					try {
						in = body.getInputStream();
						MimeMessage mime = new MimeMessage(mailSession, in);
						sentDate = df.format(mime.getSentDate());
						String subject = FileUtil.getValidFileName(
                                StringUtils.defaultIfBlank(mime.getSubject(), "UNTITLED"), 50);
						String msgMail = messageSource.getMessage("mail.label.mail", null, "", LocaleContextHolder.getLocale());    /*메일*/
						String emlFileName = "["+msgMail+"]" + subject + "_" + (i++)  + "_" + sentDate + ".eml";
						FileUtils.copyInputStreamToFile(new MimeMessageInputStream(mime), new File(path, emlFileName));
						if (i == 1) {
							title = subject;
						}
					} finally {
						if (in != null) {
							in.close();
						}
					}
				}
            }
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
		return String.valueOf(i) + "," + title;
	}

	/**
	 * 특정메일함의 모든 메일을 백업한다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 메일함 경로
	 * @param path 압축파일 생성 경로
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 * @throws java.text.ParseException
	 * @return 메일 개수
	 */
	public String backupAllMail(String userId, String folderFullName, String path)
			throws IOException, MessagingException, MailboxException, ParseException {
		MailboxSession session = null;
		int i = 0;
		String title = "";
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			MessageManager messageManager = null;
			try {
				messageManager = mailboxmanager.getMailbox(mailboxPath, session);
			} catch (MailboxNotFoundException e) { //메일함이 없으면 무시
				/*"메일함이 존재하지 않습니다."*/
				throw new AppException(messageSource.getMessage("mail.desc.notExistMailboxPlz", null, "", LocaleContextHolder.getLocale()));
			}
			Session mailSession = EmlMailUtil.getMailSession();
			MessageResultIterator iter = messageManager.getMessages(MessageRange.all(), FetchGroupImpl.FULL_CONTENT,
					session);
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			//Date today = Calendar.getInstance().getTime();
			//String now = df.format(today);
			String sentDate = "";
			while (iter.hasNext()) {
				MessageResult result = iter.next();
				//Gets the full message including headers and body.
				//The message data should have normalised line endings (CRLF).
				Content body = result.getFullContent();
				InputStream in = null;
				try {
					in = body.getInputStream();
					MimeMessage mime = new MimeMessage(mailSession, in);
					sentDate = df.format(mime.getSentDate());
					String subject = FileUtil.getValidFileName(
							StringUtils.defaultIfBlank(mime.getSubject(), "UNTITLED"), 50);
					String msgMail = messageSource.getMessage("mail.label.mail", null, "", LocaleContextHolder.getLocale());    /*메일*/
					String emlFileName = "["+msgMail+"]" + subject + "_" + (++i)  + "_" + sentDate + ".eml";
					FileUtils.copyInputStreamToFile(new MimeMessageInputStream(mime), new File(path, emlFileName));
					if (i == 1) {
						title = subject;
					}
				} finally {
					if (in != null) {
						in.close();
					}
				}
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
		return String.valueOf(i) + "," + title;
	}

	/**
	 * 여러개의 메일을 가져온다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 메일함 경로
	 * @param uids 메일 UID 목록
	 * @return mime 목록
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public List<MimeMessage> getMessages(String userId, String folderFullName, List<Long> uids)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		List<MimeMessage> mimeList = new ArrayList<MimeMessage>();
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			MessageManager messageManager = null;
			try {
				messageManager = mailboxmanager.getMailbox(mailboxPath, session);
			} catch (MailboxNotFoundException e) { //메일함이 없으면 무시
				/*"메일함이 존재하지 않습니다."*/
				throw new AppException(messageSource.getMessage("mail.desc.notExistMailboxPlz", null, "", LocaleContextHolder.getLocale()));
			}

			List<MessageRange> ranges = MessageRange.toRanges(uids);
			Session mailSession = EmlMailUtil.getMailSession();
			for (MessageRange range : ranges) {
				MessageResultIterator iter = messageManager.getMessages(range, FetchGroupImpl.FULL_CONTENT, session);
				while (iter.hasNext()) {
					MessageResult result = iter.next();
					//Gets the full message including headers and body.
					//The message data should have normalised line endings (CRLF).
					Content body = result.getFullContent();
					InputStream in = null;
					try {
						in = body.getInputStream();
						EmlMyMimeMessage msg = new EmlMyMimeMessage(mailSession, in);
						msg.setReceivedDate(result.getInternalDate());
						mimeList.add(msg);
					} catch (RuntimeException fe) {
						fe.printStackTrace();
						/*"메일이 존재하지 않습니다."*/
						throw new AppException(messageSource.getMessage("mail.desc.notExistMailPlz", null, "", LocaleContextHolder.getLocale()));
					} finally {
						if (in != null) {
							in.close();
						}
					}
				}
            }
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
		return mimeList;
	}

    public MessageIndexer.MessageMetadata getMetadata(String userId, String mailboxName, long uid) throws MailboxException {
        MailboxSession session = null;
        try {
            session = loginMailboxByUserId(userId);
            mailboxmanager.startProcessingRequest(session);
            MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), session.getUser().getUserName(), mailboxName);
            System.out.println("mailboxPath=" + mailboxPath + ", uid=" + uid);
            MessageManager messageManager = null;
            try {
                messageManager = mailboxmanager.getMailbox(mailboxPath, session);
            } catch (MailboxNotFoundException e) { //메일함이 없으면 무시
				/*"메일함이 존재하지 않습니다."*/
				throw new AppException(messageSource.getMessage("mail.desc.notExistMailboxPlz", null, "", LocaleContextHolder.getLocale()));
            }

            MessageResultIterator mri = messageManager.getMessages(MessageRange.one(uid), new MessageResult.FetchGroup() {
                @Override
                public int content() {
                    return MessageResult.FetchGroup.FULL_CONTENT;
                }

                @Override
                public Set<PartContentDescriptor> getPartContentDescriptors() {
                    return null;
                }
            }, session);

            if (mri.hasNext()) {
                final MessageResult mr = mri.next();
                return new MessageIndexer.MessageMetadata() {
                    @Override
                    public long getUid() {
                        return mr.getUid();
                    }

                    @Override
                    public String getCharset() {
                        return null;
                    }

                    @Override
                    public Flags getFlags() {
                        return mr.getFlags();
                    }

                    @Override
                    public Date getInternalDate() {
                        return mr.getInternalDate();
                    }

                    @Override
                    public long getSize() {
                        return mr.getSize();
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        try {
                            return mr.getFullContent().getInputStream();
                        } catch (MailboxException e) {
							/*"메일 인풋스트림을 얻을 수 없습니다."*/
							throw new IOException(messageSource.getMessage("mail.desc.notGetMailInputStreamPlz", null, "", LocaleContextHolder.getLocale()), e);
                        }
                    }
                };
            }
        } finally {
            if (session != null) {
                mailboxmanager.endProcessingRequest(session);
                try {
                    mailboxmanager.logout(session, true);
                } catch (MailboxException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

	/**
	 * 여러개의 메일을 가져온다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param mailboxName 메일함
	 * @param uidList uid 목록
	 * @param updateRead 메일 읽음 처리 여부
	 * @return mime 목록
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public Map<Long, MimeMessage> getMessages(String userId, String mailboxName, List<Long> uidList, boolean updateRead)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		Map<Long, MimeMessage> mimeList = new HashMap<Long, MimeMessage>();
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
//			String userKey = session.getUser().getUserName();
/*
			for (Object mailInfo : multiMap.keySet()) {
				String path = mailInfo.toString(); //메일함 경로
*/
				MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userId, mailboxName);
				MessageManager messageManager = null;
				try {
					messageManager = mailboxmanager.getMailbox(mailboxPath, session);
				} catch (MailboxNotFoundException e) { //메일함이 없으면 무시
					/*"메일함이 존재하지 않습니다."*/
					throw new AppException(messageSource.getMessage("mail.desc.notExistMailboxPlz",
							null, "", LocaleContextHolder.getLocale()));
				}

				List<MessageRange> ranges = MessageRange.toRanges(uidList);
				Session mailSession = EmlMailUtil.getMailSession();
				if (updateRead) {
					for (MessageRange range : ranges) {
						try {
							messageManager.setFlags(new Flags(Flag.RECENT), MessageManager.FlagEditMode.REMOVE, range, session);
							messageManager.setFlags(new Flags(Flag.SEEN), MessageManager.FlagEditMode.ADD, range, session);
						} catch (MailboxException e) {
							e.printStackTrace();
						}
					}
				}

				for (MessageRange range : ranges) {
					MessageResultIterator it = messageManager.getMessages(range, FetchGroupImpl.FULL_CONTENT,
							session);
					while (it.hasNext()) {
						MessageResult result = it.next();
						Content body = result.getFullContent();
						InputStream in = null;
						try {
							in = body.getInputStream();
							EmlMyMimeMessage msg = new EmlMyMimeMessage(mailSession, in);
							msg.setReceivedDate(result.getInternalDate());
							mimeList.put(result.getUid(), msg);
						} catch (RuntimeException fe) {
							fe.printStackTrace();
							/*"메일이 존재하지 않습니다."*/
							throw new AppException(messageSource.getMessage("mail.desc.notExistMailPlz",
									null, "", LocaleContextHolder.getLocale()));
						} finally {
							if (in != null) {
								in.close();
							}
						}
					}
	            }
/*
			}
*/
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
		return mimeList;
	}

	/**
	 * 여러개의 메일 상태를 변경한다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param folderFullName 메일함 경로
	 * @param uids 메일 UID 목록
	 * @param flags 메일 상태
	 * @param status true or false
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void setFlags(String userId, String folderFullName, List<Long> uids, Flags flags, boolean status)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();
			MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
			try {
				MessageManager messageManager = mailboxmanager.getMailbox(mailboxPath, session);
				List<MessageRange> ranges = MessageRange.toRanges(uids);
				for (MessageRange range : ranges) {
					messageManager.setFlags(flags, status ? MessageManager.FlagEditMode.ADD : MessageManager.FlagEditMode.REMOVE, range, session);
				}
			} catch (MailboxNotFoundException e) { //메일함이 없으면 무시
				e.printStackTrace();
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 여러개의 메일 상태를 변경한다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param multiMap 메일함 경로와 메일 UID 목록
	 * @param flags 메일 상태
	 * @param status true or false
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void setFlags(String userId, MultiValueMap multiMap, Flags flags, boolean status)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();

			for (Object mailInfo : multiMap.keySet()) {
				String mailboxPath = mailInfo.toString(); //메일함 경로
				//기존 메일함
				MailboxPath existMailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, mailboxPath);
				try {
					MessageManager messageManager = mailboxmanager.getMailbox(existMailboxPath, session);
					List<MessageRange> ranges = MessageRange.toRanges((List<Long>) multiMap.get(mailboxPath));
					for (MessageRange range : ranges) {
						messageManager.setFlags(flags, status ? MessageManager.FlagEditMode.ADD : MessageManager.FlagEditMode.REMOVE, range, session);
					}
				} catch (MailboxNotFoundException e) { //메일이 없으면 무시
					e.printStackTrace();
				}
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 모두 읽음 처리한다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param boxPathList 메일함 경로 목록
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void setAllSeenFlags(String userId, List<String> boxPathList)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();

			for (String folderFullName : boxPathList) {
				MailboxPath mailboxPath = new MailboxPath(session.getPersonalSpace(), userKey, folderFullName);
				try {
					MessageManager messageManager = mailboxmanager.getMailbox(mailboxPath, session);
					SearchQuery searchQuery = new SearchQuery();
					searchQuery.andCriteria(SearchQuery.flagIsUnSet(Flag.SEEN));
					Iterator<Long> searchList = messageManager.search(searchQuery, session);
					Collection<Long> uidList = new ArrayList<Long>();
					while (searchList.hasNext()) {
						uidList.add(searchList.next());
		            }
					List<MessageRange> rangeList = MessageRange.toRanges(uidList);
					for (MessageRange messageRange : rangeList) {
						messageManager.setFlags(new Flags(Flag.SEEN), MessageManager.FlagEditMode.ADD, messageRange, session);
					}
				} catch (MailboxNotFoundException e) { //메일함이 없으면 무시
					e.printStackTrace();
				}
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}

	/**
	 * 여러개의 메일의 라벨 붙임 상태를 변경한다.
	 * @param userId 소유자의 메일 주소 또는 이메일 아이디(EML_USER 테이블 PK)
	 * @param multiMap 메일함 경로와 메일 UID, 플래그 값(라벨 아이디, 전달, 별표)
	 * @param status true or false
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 */
	public void setFlags(String userId, MultiValueMap multiMap, boolean status)
			throws IOException, MessagingException, MailboxException {
		MailboxSession session = null;
		try {
			session = loginMailboxByUserId(userId);
			mailboxmanager.startProcessingRequest(session);
			String userKey = session.getUser().getUserName();

			for (Object mailInfo : multiMap.keySet()) {
				EmlMailKeyVo mailKey = (EmlMailKeyVo) mailInfo; //메일함 경로
				//기존 메일함
				MailboxPath existMailboxPath = new MailboxPath(session.getPersonalSpace(), userKey,
						mailKey.getMailboxName());
				try {
					MessageManager messageManager = mailboxmanager.getMailbox(existMailboxPath, session);
					List<String> targetList = (List<String>) multiMap.get(mailKey);
					Flags flags = new Flags();
					for (String targetId : targetList) {
						if (StringUtils.isEmpty(targetId)) {
							continue;
						}
						flags.add(targetId);
					}
					if (targetList != null && targetList.size() > 0) {
						messageManager.setFlags(flags, status ? MessageManager.FlagEditMode.ADD : MessageManager.FlagEditMode.REMOVE, MessageRange.one(mailKey.getUid()), session);
					}
				} catch (MailboxNotFoundException e) { //메일이 없으면 무시
					e.printStackTrace();
				}
			}
		} finally {
			 if (session != null) {
				 mailboxmanager.endProcessingRequest(session);
	             try {
	            	 mailboxmanager.logout(session, true);
	             } catch (MailboxException e) {
	               	e.printStackTrace();
	             }
	         }
		}
	}
}
