package com.oreumio.james.rest.send;

import com.oreumio.james.rest.AppException;
import com.oreumio.james.rest.file.EmlFile;
import com.oreumio.james.rest.form.EmlMailFormVo;
import com.oreumio.james.rest.util.EmlMailUtil;
import com.oreumio.james.util.IdProvider;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 메일 MIME MESSAGE에 관련 모든 작업 처리
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Component
public class EmlMessageUtilService {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlMessageUtilService.class);

	/**
	 * 임시 업로드 경로
	 */
	@Value("${upload.tempDir}")
	private String uploadTempDir;

    @Value("${url.mailServer}")
    private String mailServer;

	/**
	 * 기본 다국어 코드
	 */
	@Value("${install.language}")
    private String defaultLangCode;

	@Autowired
    private ServletContext servletContext;

	@Autowired
	private MessageSource messageSource;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

	/**
	 * 메일 MIME 원문을 리턴
	 * @param message 메일 mime
	 * @return 메일 MIME 원문
	 * @throws java.io.IOException e1
	 * @throws javax.mail.MessagingException e2
	 */
	public String getOriginalMessage(MimeMessage message) throws IOException, MessagingException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		message.writeTo(bos);
		return new String(bos.toByteArray());
	}

	/**
	 * 메일 MIME 을 vo 로 변경
	 * @param message 메일 mime
	 * @return 메일 MIME vo
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 */
	public EmlMessageVo parseMimeMessage(MimeMessage message) throws MessagingException, IOException {
		return parseMimeMessage(message, null, false, null, false);
	}

	/**
	 * 메일 MIME 을 vo 로 변경
	 * @param message 메일 mime
	 * @param charset 인코딩
	 * @return 메일 MIME vo
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 */
	public EmlMessageVo parseMimeMessage(MimeMessage message, String charset) throws MessagingException,
		IOException {
		return parseMimeMessage(message, charset, false, null, false);
	}

	/**
	 * 메일 MIME 을 vo 로 변경
	 * @param message 메일 mime
	 * @param charset 인코딩
	 * @param createAttach 첨부파일 생성 여부
	 * @param groupId 그룹 아이디
	 * @param xssFilterYn 본문 내용 xss 필터 적용 여부
	 * @return 메일 MIME vo
	 * @throws javax.mail.MessagingException
	 * @throws java.io.IOException
	 */
	public EmlMessageVo parseMimeMessage(MimeMessage message, String charset, boolean createAttach, String groupId,
			boolean xssFilterYn) throws MessagingException, IOException {
		EmlMessageVo messageVo = new EmlMessageVo();
		MimeMessageParsingHelper messageProxy = MimeMessageParsingHelper.getMimeMessageParsingProxy(message, charset,
				servletContext.getContextPath());
		messageVo.setPrioritized(messageProxy.isPrioritized());
		messageVo.setSecured(messageProxy.isSecured());
		messageVo.setSubject(StringUtils.defaultIfBlank(messageProxy.getSubject(),
				messageSource.getMessage("mail.label.noTitle", null, "", LocaleContextHolder.getLocale())));
		messageVo.setCharset(messageProxy.getCharset());
		messageVo.setFrom(messageProxy.getSenderDisp());
		messageVo.setOrgFrom(messageProxy.getSenderOrg());
		messageVo.setTo(messageProxy.getRecipients());
		messageVo.setCc(messageProxy.getAddresses("Cc"));
		messageVo.setBcc(messageProxy.getAddresses("Bcc"));
		messageVo.setContent(messageProxy.getImgHtml(servletContext.getContextPath(), xssFilterYn));
		messageVo.setSendDt(messageProxy.getSentDate());
		Date receivedDate = message.getReceivedDate(); //받은날짜
		messageVo.setRecvDt(receivedDate == null ? messageVo.getSendDt() : receivedDate);
		messageVo.setTimeZoneId(messageProxy.getTimeZoneId());
		messageVo.setPassword(messageProxy.getPassword());
		if (messageProxy.getHeader("X-BuddyYn") != null) { //계열사 친구 맺기 여부(임시 저장/예약 메일 수정시 이용)
			messageVo.setBuddyJoinRecpinYn("Y");
		}
		//예약 메일 작성시 셋팅된 커스텀 헤더값 확인후 예약 날짜와 시간 셋팅
		if (StringUtils.isNotEmpty(messageVo.getTimeZoneId())) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			formatter.setTimeZone(TimeZone.getTimeZone(messageVo.getTimeZoneId()));
			String dateStr = formatter.format(messageProxy.getSentDate());
			String[] dateStrArr = dateStr.split(" ");
			messageVo.setReservDt(dateStrArr[0]);
			messageVo.setReservTime(dateStrArr[1]);
		}

		List<Integer> indexList = null;
		if (createAttach) {
			indexList = new ArrayList<Integer>();
			indexList.add(-1); //모든 첨부 파일 생성
		}

		List<EmlFile> fileList = messageProxy.getCmmFileList(messageProxy, indexList);
		if (StringUtils.isNotEmpty(groupId)) {
			for (EmlFile file : fileList) {
				file.setGroupId(groupId);
			}
		}
		messageVo.setAttachFileList(fileList);
		return messageVo;
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
	 * 메일 vo 를  메일 MIME으로 변경
	 * @param mailFormVo 메일 작성 vo
	 * @return 메일 message
	 * @throws javax.mail.MessagingException
	 * @throws java.io.UnsupportedEncodingException
	 * @throws java.text.ParseException
	 */
    public MimeMessage generateMime(EmlMailFormVo mailFormVo, String readId) throws MessagingException, UnsupportedEncodingException, ParseException, UnknownHostException {
		MimeMessage message = new MimeMessage(EmlMailUtil.getMailSession());

        // Received 헤더 설정
        setReceivedDate(message, mailFormVo.getRemoteAddr());

		// 보낸 사람 조직도 정보
		if (StringUtils.isNotEmpty(mailFormVo.getMailFrom().get(0).getPersonal())) {
			InternetAddress orgAddress = new InternetAddress(mailFormVo.getMailFrom().get(0).getAddress(), mailFormVo.getMailFrom().get(0).getPersonal(), "utf-8");
			message.setHeader("X-From", orgAddress.toString());
		}

		// 보안메일 여부
		if ("Y".equals(mailFormVo.getPasswordYn())) {
			message.setHeader("X-Secured", "Y");
			message.setHeader("X-Password", mailFormVo.getPassword()); //임시 저장,예약메일 수정시 사용(발송할 때 커스텀 헤더 삭제됨)
		}

		// 중요도 적용
		if (!StringUtils.isEmpty(mailFormVo.getPriority())) {
			message.setHeader("X-Priority", mailFormVo.getPriority());
		}

        // Date
        message.setSentDate(new Date());

        // From
		InternetAddress fromAddress = null;
		if (StringUtils.isNotEmpty(mailFormVo.getMailFrom().get(0).getPersonal())) {
			fromAddress = new InternetAddress(mailFormVo.getMailFrom().get(0).getAddress(), mailFormVo.getMailFrom().get(0).getPersonal(), "utf-8");
		} else {
			fromAddress = new InternetAddress(mailFormVo.getMailFrom().get(0).getAddress());
		}
		message.setFrom(fromAddress);

		// To
		if (mailFormVo.getMailTo() != null && mailFormVo.getMailTo().size() > 0) {
			InternetAddress[] toAddress = null;

			try {
				toAddress = EmlMailUtil.getInternetAddress(mailFormVo.getMailTo());
			} catch (UnsupportedEncodingException e) {
                /*"이메일 주소 형식이 올바르지 않습니다."*/
				throw new AppException(messageSource.getMessage("mail.desc.incorrectEmailAddressType", null, "",
						LocaleContextHolder.getLocale()));
			}

			message.setRecipients(Message.RecipientType.TO,	toAddress);
		}

		if (mailFormVo.getMailCc() != null && mailFormVo.getMailCc().size() > 0) {
			InternetAddress[] ccAddress = null;

			try {
				ccAddress = EmlMailUtil.getInternetAddress(mailFormVo.getMailCc());
			} catch (UnsupportedEncodingException e) {
                /*"이메일 주소 형식이 올바르지 않습니다."*/

				throw new AppException(messageSource.getMessage("mail.desc.incorrectEmailAddressType", null, "",
						LocaleContextHolder.getLocale()));
			}

			message.addRecipients(Message.RecipientType.CC, ccAddress);
		}

        // BCC

/*
		if (mailFormVo.getMailBccs() != null && mailFormVo.getMailBccs().size() > 0) {
			InternetAddress[] bccAddress = null;

			try {
				bccAddress = EmlMailUtil.getInternetAddress(mailFormVo.getMailBccs());
			} catch (UnsupportedEncodingException e) {
                // "이메일 주소 형식이 올바르지 않습니다."

				throw new AppException(messageSource.getMessage("mail.desc.incorrectEmailAddressType", null, "",
						LocaleContextHolder.getLocale()));
			}

			message.addRecipients(Message.RecipientType.BCC, bccAddress);
		}
*/

        Multipart multipart = setMultipart(mailFormVo.getContent(), mailFormVo.getFileList(), "utf-8");
        message.setContent(multipart);

		message.setContentLanguage(new String[]{StringUtils.defaultIfBlank(mailFormVo.getLangCd(), defaultLangCode)});

		//Subject
		message.setSubject(MimeUtility.encodeText(StringUtils.defaultString(mailFormVo.getSubject()), "utf-8", "B"));
		//시스템 메일 발송이 아닌 일반 사용자끼리 발송일 경우 반드시 deptId가 셋팅되야 함
/*
		if (StringUtils.isNotEmpty(mailFormVo.getDeptId())) {
			message.setHeader("X-DeptId", mailFormVo.getDeptId()); //승인메일 필터링에서 사용(부서장 승인)
		}
*/
		message.saveChanges();
		return message;
	}

	/**
	 * text 메일 내용과 첨부파일 BodyPart 생성
	 *
	 * @param content 메일 작성 vo
	 * @param fileList 첨부파일 목록
	 * @param encoding 인코딩 타입
	 * @return Multipart
	 * @throws javax.mail.MessagingException
	 * @throws java.io.UnsupportedEncodingException
	 */
	private Multipart setMultipart(String content, List<String> fileList, String encoding)
			throws MessagingException, UnsupportedEncodingException {
		BodyPart messageBodyPart;
		BodyPart imageBodyPart;
		Multipart relatedPart = null;
		Multipart alternativePart = null;
		Multipart mixedPart = null;
		BodyPart contentWrapper = null;
		DataSource source;

		Map<String, String> imgMap = null; //extractImageSrc(mailFormVo);

		alternativePart = new MimeMultipart("alternative");

		messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(Jsoup.parse(content).text(), "text/plain; charset=" + encoding);
		messageBodyPart.setHeader("Content-Transfer-Encoding", "base64");
		alternativePart.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(content, "text/html; charset=" + encoding);
		messageBodyPart.setHeader("Content-Transfer-Encoding", "base64");

		//본문에 이미지가 있는 경우
		if (imgMap != null) {
			relatedPart = new MimeMultipart("related");
		}

		if (relatedPart != null) {
			relatedPart.addBodyPart(messageBodyPart);
			contentWrapper = new MimeBodyPart();
			contentWrapper.setContent(relatedPart, "multipart/alternative");
		    alternativePart.addBodyPart(contentWrapper);

		    for (Map.Entry<String, String> entry : imgMap.entrySet()) {
				imageBodyPart = new MimeBodyPart();
				FileDataSource fileData = new FileDataSource(entry.getValue());
                imageBodyPart.setDataHandler(new DataHandler(fileData));
                imageBodyPart.setHeader("Content-ID", entry.getKey());
                imageBodyPart.setFileName(fileData.getName());
                imageBodyPart.setDisposition(MimeBodyPart.INLINE);
                relatedPart.addBodyPart(imageBodyPart);
			}
		} else {
			alternativePart.addBodyPart(messageBodyPart);
		}

/*
		if (!"Y".equals(mailFormVo.getMassiveMailYn()) && fileList != null && fileList.size() > 0) {
			mixedPart = new MimeMultipart("mixed");
		    contentWrapper = new MimeBodyPart();
		    contentWrapper.setContent(alternativePart);
		    mixedPart.addBodyPart(contentWrapper);

		    for (String file : fileList) {
                EmlFile emlFile = null;
				messageBodyPart = new MimeBodyPart();
				source = new FileDataSource(uploadTempDir + File.separator + emlFile.getFilePath()
						+ File.separator + emlFile.getSaveFileName());
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(MimeUtility.encodeText(emlFile.getFileName(), encoding, "B"));
				mixedPart.addBodyPart(messageBodyPart);
			}
		}
*/

		if (mixedPart != null) {
			return mixedPart;
		} else {
			return alternativePart;
		}
	}

	/**
	 * image tag 에 src 속성을 찾아서 cid 를 이미지 url 변경
	 * @param mailFormVo 메일 작성 vo
	 * @return cid 목록
	 */
	private Map<String, String> extractImageSrc(EmlMailFormVo mailFormVo) {
		Map<String, String> imgMap = new HashMap<String, String>();
		String content = mailFormVo.getContent();
		Pattern p = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>",
				Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		Matcher m = p.matcher(content);
		while (m.find()) {
			if (m.group().indexOf("/viewImage.do") > -1) { //에디터에서 삽입된 이미지만 MIME 에 추가한다.
				String imgSrc = m.group(1);
				String imageUrl = extractParamValue(imgSrc);

				imageUrl = uploadTempDir + File.separator + imageUrl;
				File imgFile = new File(imageUrl);
				if (imgFile.exists()) {
					String cid = idProvider.next();
					content = content.replace(imgSrc, "cid:" + cid);
					try {
						imgMap.put("<" + cid + ">", imgFile.getCanonicalPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		mailFormVo.setContent(content);
		return imgMap;
	}

	/**
	 * 이미지 url에서 파일 경로 리턴
	 * @param url 이미지 경로
	 * @return 이미지 경로
	 */
	private String extractParamValue(String url) {
		Map<String, String> path = EmlMailUtil.splitQuery(url);
		return StringUtils.defaultString(path.get("filePath"));
	}

	/**
	 * MIME 메세지의 본문 내용을 변경한다
	 * @param message 메일 mime
	 * @param body 바꿀 내용
	 * @return MimeMessage
	 * @throws java.io.IOException
	 * @throws javax.mail.MessagingException
	 */
	public MimeMessage replaceMessageBody(MimeMessage message, String body)
			throws MessagingException, IOException {
	    Object content = message.getContent();
	    if (content instanceof Multipart) {
	        replaceMessageBody((Multipart) content, body);
	    } else {
	        message.setContent(body, message.getContentType());
	    }
	    message.saveChanges();
	    return message;
	}

	/**
	 * 재귀 호출로 MIME 메세지의 본문 내용을 변경한다
	 * @param multipart mime part
	 * @param body 변경할 내용
	 * @throws java.io.IOException
	 * @throws javax.mail.MessagingException
	 */
	public void replaceMessageBody(Multipart multipart, String body)
			throws MessagingException, IOException {
		body = StringUtils.defaultString(body);
		for (int i = 0; i < multipart.getCount(); i++) {
			BodyPart bodyPart = multipart.getBodyPart(i);
			String disposition = bodyPart.getDisposition();
			// It's not an attachment
		    if (disposition == null && bodyPart instanceof MimeBodyPart) {
			    MimeBodyPart mbp = (MimeBodyPart) bodyPart;

		        if (mbp.isMimeType("text/plain")) {
		        	bodyPart.setContent(StringUtils.defaultString(Jsoup.parse(body).text()), bodyPart.getContentType());
		        } else if (mbp.isMimeType("text/html")) {
		            bodyPart.setContent(body, bodyPart.getContentType());
		        } else if (mbp.isMimeType("multipart/*")) {
		        	Object subContent = bodyPart.getContent();
		            if (subContent.getClass().isAssignableFrom(MimeMultipart.class)) {
		            	MimeMultipart subMimeMultipart = (MimeMultipart) subContent;
		         	    replaceMessageBody(subMimeMultipart, body);
		            }
		        }
		    }
	    }
	}

    /**
     * MIME 에서 본문 내용을 가져온다.
     * @param p mime part
     * @return 본문 내용
     * @throws javax.mail.MessagingException e1
     * @throws java.io.IOException e2
     */
    public String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null) {
                        text = getText(bp);
                    }
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null) {
                        return s;
                    }
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null) {
                    return s;
                }
            }
        }

        return null;
    }
}
