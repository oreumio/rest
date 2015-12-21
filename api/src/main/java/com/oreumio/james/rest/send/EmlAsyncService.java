package com.oreumio.james.rest.send;

import com.oreumio.james.core.MailImpl;
import com.oreumio.james.core.MimeMessageCopyOnWriteProxy;
import com.oreumio.james.domainlist.DomainList;
import com.oreumio.james.domainlist.DomainListException;
import com.oreumio.james.log.TransportLogRepository;
import com.oreumio.james.mailbox.store.Poster;
import com.oreumio.james.mime.InternetHeadersInputStream;
import com.oreumio.james.repository.SingletonRepository;
import com.oreumio.james.rest.file.EmlFile;
import com.oreumio.james.rest.group.EmlGroupDomainDao;
import com.oreumio.james.rest.user.EmlUser;
import com.oreumio.james.rest.user.EmlUserDao;
import com.oreumio.james.rest.user.EmlUserSupportService;
import com.oreumio.james.rest.util.DateUtil;
import com.oreumio.james.rest.util.EmlCommonConstants;
import com.oreumio.james.rest.util.EmlMailUtil;
import com.oreumio.james.rest.util.IdGenerator;
import com.oreumio.james.rrt.RecipientRewriteTable;
import com.oreumio.james.rrt.RecipientRewriteTable.ErrorMappingException;
import com.oreumio.james.rrt.RecipientRewriteTableException;
import com.oreumio.james.track.TrackRepository;
import com.oreumio.james.user.UsersRepository;
import com.oreumio.james.user.UsersRepositoryException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.james.lifecycle.LifecycleUtil;
import org.apache.james.mailbox.exception.MailboxException;
import org.apache.james.mime4j.dom.datetime.DateTime;
import org.apache.james.mime4j.field.datetime.parser.DateTimeParser;
import org.apache.james.queue.MailQueue;
import org.apache.james.queue.MailQueueFactory;
import org.apache.mailet.MailAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

/**
 * <pre>
 * 메일 비동기 발송
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlAsyncService {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlAsyncService.class);

    @Value("${backup.enabled}")
    private boolean backupEnabled;

    @Value("${backup.store}")
    private String backupStore;

    /**
	 * 메일 서버 URL
	 */
	@Value("${url.mailServer}")
    private String mailServer;

	/**
	 * 기본 언어 코드
	 */
	@Value("${install.language}")
	private String defaultLangCode;

	/**
	 * 메일 MIME 파싱 관련 처리
	 */
	@Autowired
	private EmlMessageUtilService emlMessageUtilService;

	/**
	 * 메일함 api 관련 처리
	 */
	@Autowired
	private EmlMailboxUtilService emlMailboxUtilService;

	/**
	 * 멀티 도메인 DB 처리
	 */
	@Autowired
	private EmlGroupDomainDao emlGroupDomainDao;

	/**
	 * 메일 유저 DB 처리
	 */
	@Autowired
	private EmlUserDao emlUserDao;

	/**
	 * 수신확인 상세 DB 처리
	 */
	@Autowired
	private EmlReadConfrimDao emlReadConfrimDao;

	/**
	 * 수신확인 DB 처리
	 */
	@Autowired
	private EmlReadConfrimMailDao emlReadConfrimMailDao;

	/**
	 * 조직도 처리
	 */
	@Autowired
	private EmlUserSupportService emlUserSupportService;

	/**
	 * 메일 발송 큐
	 */
	private MailQueue queue;

	/**
	 * 메일 발송 팩토리
	 */
	@Resource(name = "mailqueuefactory")
	private MailQueueFactory queueFactory;

    /**
     * 메일 백업을 위한 기능
     */
    private Poster poster;

	/**
	 * 메일 계정 확인
	 */
	@Autowired
	private UsersRepository usersRepository;

	/**
	 * 도메인 체크
	 */
	@Autowired
	private DomainList domainList;

	@Autowired
	private MessageSource messageSource;

	/**
	 * 대표메일 처리
	 */
	@Resource(name = "recipientrewritetable")
	private RecipientRewriteTable rrt;

	/**
	 * MIME 한건만 보관 처리
	 */
	@Resource(name = "singletonrepository")
    private SingletonRepository singletonRepository;

	/**
	 * 발송추적 처리
	 */
	private TrackRepository trackRepository;

	private TransportLogRepository transportLogRepository;

	/**
	 * @param transportLogRepository
	 */
	@Resource(name = "logrepository")
    public void setTransportLogRepository(TransportLogRepository transportLogRepository) {
        this.transportLogRepository = transportLogRepository;
    }

	/**
	 * @param emlMessageUtilService the emlMessageUtilService to set
	 */
	public void setEmlMessageUtilService(EmlMessageUtilService emlMessageUtilService) {
		this.emlMessageUtilService = emlMessageUtilService;
	}

	/**
	 * @param emlMailboxUtilService the emlMailboxUtilService to set
	 */
	public void setEmlMailboxUtilService(EmlMailboxUtilService emlMailboxUtilService) {
		this.emlMailboxUtilService = emlMailboxUtilService;
	}

	/**
	 * @param emlGroupDomainDao the emlGroupDomainDao to set
	 */
	public void setEmlGroupDomainDao(EmlGroupDomainDao emlGroupDomainDao) {
		this.emlGroupDomainDao = emlGroupDomainDao;
	}

	/**
	 * @param emlUserDao the emlUserDao to set
	 */
	public void setEmlUserDao(EmlUserDao emlUserDao) {
		this.emlUserDao = emlUserDao;
	}

	/**
	 * @param emlReadConfrimDao the emlReadConfrimDao to set
	 */
	public void setEmlReadConfrimDao(EmlReadConfrimDao emlReadConfrimDao) {
		this.emlReadConfrimDao = emlReadConfrimDao;
	}

	/**
	 * @param emlReadConfrimMailDao the emlReadConfrimMailDao to set
	 */
	public void setEmlReadConfrimMailDao(EmlReadConfrimMailDao emlReadConfrimMailDao) {
		this.emlReadConfrimMailDao = emlReadConfrimMailDao;
	}

	/**
	 * @param queue the queue to set
	 */
	public void setQueue(MailQueue queue) {
		this.queue = queue;
	}

	/**
	 * @param queueFactory the queueFactory to set
	 */
	public void setQueueFactory(MailQueueFactory queueFactory) {
		this.queueFactory = queueFactory;
	}

	/**
	 * @param queueFactory 메일 발송 팩토리
	 */
	public void setMailQueueFactory(MailQueueFactory queueFactory) {
	    this.queueFactory = queueFactory;
	}

	/**
	 * @param usersRepository the usersRepository to set
	 */
	public void setUsersRepository(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	/**
	 * @param domainList the domainList to set
	 */
	public void setDomainList(DomainList domainList) {
		this.domainList = domainList;
	}

	/**
	 * @param rrt the rrt to set
	 */
	public void setRrt(RecipientRewriteTable rrt) {
		this.rrt = rrt;
	}

	/**
	 * @param trackRepository
	 */
	@Resource(name = "trackRepository")
    public void setTrackRepository(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Resource(name = "poster")
    public void setPoster(Poster poster) {
        this.poster = poster;
    }

	/**
	 * @param singletonRepository the singletonRepository to set
	 */
	public void setSingletonRepository(SingletonRepository singletonRepository) {
		this.singletonRepository = singletonRepository;
	}

	/**
	 * 메일 발송 큐 설정
	 */
	@PostConstruct
	public void init() {
	    this.queue = queueFactory.getQueue(MailQueueFactory.SPOOL);
	}

	/**
	 * 메일 발송 처리
	 * 승인 메일 필터링에 걸리게 할 메일은 반드시 X-DeptId 헤더값을 셋팅할 것
     *
     * TODO 메일 발송 큐에 넣기 전, 트랜잭션은 완료돼 있어야 함.
     *
	 * {@literal @}Async 비동기로 실행
	 * @param emailId 보내는 사람 이메일 아이디
	 * @param msgId 보낸 메일에 저장된 메세지 아이디
	 * @param message 메일 mime
	 * @param individualYn 개별 메일 여부
	 * @param mailReadRecpinYn 수신확인 응답 메일 받기
	 * @param langCd 다국어 코드(조직도 정보 추출할 때 사용)
	 * @param remoteAddr 발송자 아이피
	 * @throws Exception
	 */
	public void sendMail(String emailId, String msgId, MimeMessage message, String individualYn, String mailReadRecpinYn, String langCd, String remoteAddr)
					throws Exception {
        // Received 헤더 설정
		setReceivedDate(message, remoteAddr);
		message.saveChanges();

        // 큐에 투입할 객체 생성
		List<MailImpl> mailList = null; //insertRecvConfirmAndConvertMail(emailId, msgId, message, individualYn, mailReadRecpinYn, langCd);

		String groupId = ""; //발송자 회사 고유번호
	    for (MailImpl mail : mailList) {
			try {
				queue.enQueue(mail);
				trackRepository.logEvent(mail, "MT"); //발송 추적
				try {
                    groupId = (String) mail.getAttribute("companyid");
					transportLogRepository.log(groupId, DateUtil.getDate("yyyyMMdd"), "R"); //수신
				} catch (Exception e) {
					LOGGER.warn("수신 메일 건수를 로깅할 수없습니다.", e);
				}
			} finally {
				LifecycleUtil.dispose(mail);
			}
		}

	    if (mailList.size() > 0) {
	    	try {
	    		transportLogRepository.log(groupId, DateUtil.getDate("yyyyMMdd"), "S"); //발송
	    	} catch (Exception e) {
				LOGGER.warn("발송 메일 건수를 로깅할 수없습니다.", e);
			}
	    }
	}

	/**
	 * 메세지 헤더 receivedDate 셋팅
	 * @param message 메세지
	 * @param remoteAddr 발송자 아이피
	 * @throws java.net.UnknownHostException
	 * @throws javax.mail.MessagingException
	 */
	private void setReceivedDate(MimeMessage message, String remoteAddr) throws UnknownHostException,
		MessagingException {
		StringBuilder headerLineBuffer = new StringBuilder();
		remoteAddr = StringUtils.defaultIfBlank(remoteAddr, InetAddress.getLocalHost().getHostAddress());
		headerLineBuffer.append("from ").append(remoteAddr);
        headerLineBuffer.append(" ([").append(remoteAddr).append("])\n");
        headerLineBuffer.append(" by ").append(StringUtils.removeStart(mailServer, "http://"))
        	.append(" (").append("james Server").append(") with ").append("WEB;");
        headerLineBuffer.append((new MailDateFormat()).format(new Date()).toString());
        message.setHeader("Received", headerLineBuffer.toString());
	}

	/**
	 * 수신자 메일의 대표메일 정보를 풀어서 리턴
	 * @param addrList 수신자 이메일 주소 목록
	 * @param invalidAddrList 잘못된 메일 주소
	 * @param exceedQuotaAddrList 메일함 용량이 남아 있지 않은 수신자의 메일 주소 목록
	 * @param langCode 다국어 코드
	 * @param orgEmailList 내부 사용자 이메일 목록
	 * @return 수신자 이메일 주소 목록
	 * @throws com.oreumio.james.rrt.RecipientRewriteTableException
	 * @throws com.oreumio.james.rrt.RecipientRewriteTable.ErrorMappingException
	 * @throws javax.mail.internet.AddressException
	 * @throws com.oreumio.james.user.UsersRepositoryException
	 * @throws com.oreumio.james.domainlist.DomainListException
	 * @throws java.io.UnsupportedEncodingException
	 */
//	@Transactional(value = "rest_tm", readOnly = true)
	private Map<String, InternetAddress> getRecipients(Address[] addrList, List<String> invalidAddrList,
			List<String> exceedQuotaAddrList, String langCode, Map<String, String> orgEmailList) throws ErrorMappingException,
			RecipientRewriteTableException, AddressException, DomainListException, UsersRepositoryException,
			UnsupportedEncodingException {
		List<MailAddress> allRecipients = new ArrayList<MailAddress>(); //InternetAddress 에서 MailAddress 로 변환하기 위한 저장 공간
		//대표메일 풀어진 주소(수신확인 테이블 입력용으로도 사용)
		Map<String, InternetAddress> rcpAddrList = new HashMap<String, InternetAddress>();
		for (Address subAddr : addrList) {
			InternetAddress addr = (InternetAddress) subAddr;
			if (addr.isGroup()) { //그룹 수신자라면
				InternetAddress[] groupList = addr.getGroup(false);
				for (InternetAddress group : groupList) {
					rcpAddrList.put(group.getAddress(), group); //수신자 목록
				}
			} else {
				rcpAddrList.put(addr.getAddress(), addr); //수신자 목록
			}
		}
		Iterator<String> keys = rcpAddrList.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            allRecipients.add(new MailAddress(key)); //이름 빼고 순수하게 이메일 주소만으로 주소목록 생성
        }

		for (MailAddress recipient : allRecipients) {
			// 대표메일인 경우 대표메일에 포함되어 있는 메일 주소 목록들을 추출
			String recpEmail = recipient.toString();
			Collection<String> members = rrt.getMappings(recipient.getLocalPart(), recipient.getDomain());
			for (String member : members) {
				//대표메일 주소가 아니라면
				if (member.equals(recpEmail)) {
					String originalAddress = MimeUtility.decodeText(rcpAddrList.get(member).toString());
					//회사 도메인이면서 대표메일이 아닌 직원의 메일이 없는 계정이거나 퇴사한 경우 발송자의 받은 메일함에 실패 내역을 저장시키기 위해
					//퇴사한 사용자이지만 메일 수신을 받도록 설정된 경우에는 통과
					if (domainList.containsDomain(recipient.getDomain())
						&& !usersRepository.contains(member)) {
						if (!invalidAddrList.contains(originalAddress)) {
							invalidAddrList.add(originalAddress);
						}
						rcpAddrList.remove(member); //수신자에서 제외
						continue;
					}

					String personal = emlUserSupportService.getPersonal(member, langCode);
					if (personal != null) {
						//email_id값이 같은 수신자 메일 주소는 제외
						if (orgEmailList.containsValue(member)) {
							rcpAddrList.remove(member); //수신자에서 제외
							continue;
						}
						EmlUser user = emlUserDao.selectUser(member);
						long usage = emlUserDao.selectMailUsage(member);
						if (user.getQuota() - usage <= 0) { //수신자의 남은 용량 확인
							if (!exceedQuotaAddrList.contains(originalAddress)) {
								exceedQuotaAddrList.add(originalAddress);
								rcpAddrList.remove(member); //수신자에서 제외
							}
						}
						orgEmailList.put(member, member);
					}
				} else { //대표메일 풀어진 주소라면
					if (rcpAddrList.containsKey(recpEmail)) {
						rcpAddrList.remove(recpEmail); //원래 수신자 메일 주소 목록에서 대표메일 주소 삭제하고 풀어진 메일 주소 입력
					}

                    String personal = emlUserSupportService.getPersonal(member, langCode);
					if (personal != null) {
						//email_id값이 같은 수신자 메일 주소는 제외
						if (orgEmailList.containsValue(member)) {
							continue;
						}
						EmlUser user = emlUserDao.selectUser(member);
						long usage = emlUserDao.selectMailUsage(member);
						if (user.getQuota() - usage <= 0) { //수신자의 남은 용량 확인
							String exceedEmail = new InternetAddress(member, personal, "utf-8").toString();
							if (!exceedQuotaAddrList.contains(exceedEmail)) {
								exceedQuotaAddrList.add(exceedEmail);
							}
						} else {
							rcpAddrList.put(member, new InternetAddress(member, personal, "utf-8"));
						}
						orgEmailList.put(member, member);
					} else {
						rcpAddrList.put(member, new InternetAddress(member));
					}
				}
			}
		}
		return rcpAddrList;
	}

	/**
	 * 테스트 데이터 입력
	 * @param request the request
	 * @param emailId 이메일 아이디
	 * @param folderFullName 저장될 메일함 경로
	 * @param cnt 입력 횟수
	 * @throws org.apache.james.mailbox.exception.MailboxException
	 * @throws java.io.IOException
	 */
	public void insertTestData(HttpServletRequest request, String emailId, String folderFullName, int cnt)
			throws MailboxException, IOException {
		String samplePath = request.getRealPath("/") + "sample.eml";
		emlMailboxUtilService.appendMessage(emailId, folderFullName,
				FileUtils.readFileToString(new File(samplePath), "utf-8"), cnt);
	}
}
