package com.oreumio.james.rest.send;

import com.oreumio.james.core.MailImpl;
import com.oreumio.james.core.MimeMessageCopyOnWriteProxy;
import com.oreumio.james.core.MimeMessageInputStream;
import com.oreumio.james.domainlist.DomainList;
import com.oreumio.james.domainlist.DomainListException;
import com.oreumio.james.log.TransportLogRepository;
import com.oreumio.james.mime.InternetHeadersInputStream;
import com.oreumio.james.repository.SingletonRepository;
import com.oreumio.james.rest.AppException;
import com.oreumio.james.rest.file.EmlFile;
import com.oreumio.james.rest.file.EmlFileDao;
import com.oreumio.james.rest.form.*;
import com.oreumio.james.rest.message.EmlMailDao;
import com.oreumio.james.rest.user.EmlUser;
import com.oreumio.james.rest.user.EmlUserDao;
import com.oreumio.james.rest.user.EmlUserSupportService;
import com.oreumio.james.rest.util.*;
import com.oreumio.james.rrt.RecipientRewriteTable;
import com.oreumio.james.rrt.RecipientRewriteTableException;
import com.oreumio.james.track.TrackRepository;
import com.oreumio.james.user.UsersRepository;
import com.oreumio.james.user.UsersRepositoryException;
import com.oreumio.james.util.IdProvider;
import org.apache.commons.collections.map.MultiValueMap;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class SendService {
    static final Logger LOGGER = LoggerFactory.getLogger(SendService.class);

    private String uploadTempDir;

    private String webBugUrl = "http://localhost";

    private String imageServerUrl;

    @Value("${install.language}")
    private String defaultLangCode;

    /**
     * 메일 서버 URL
     */
    @Value("${url.mailServer}")
    private String mailServer;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    /**
     * 메일함 api 관련 처리
     */
    @Autowired
    private EmlMailboxUtilService emlMailboxUtilService;

    /**
     * 메일 MIME 파싱 관련 처리
     */
    @Autowired
    private EmlMessageUtilService emlMessageUtilService;

    /**
     * 메일 비동기 발송 처리
     */
    @Autowired
    private EmlAsyncService emlAsyncService;

    @Autowired
    private EmlMailFormDao emlMailFormDao;

    @Autowired
    private EmlFileDao emlFileDao;

    @Autowired
    private EmlMassiveMailDao emlMassiveMailDao;

    @Autowired
    private EmlMassiveMailDetailDao emlMassiveMailDetailDao;


    @Autowired
    private EmlRecentlyUsedRecipientDao emlRecentlyUsedRecipientDao;

    @Autowired
    private MessageSource messageSource;

    /**
     * 메일 유저 DB 처리
     */
    @Autowired
    private EmlUserDao emlUserDao;

    /**
     * 도메인 체크
     */
    @Autowired
    private DomainList domainList;

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
     * 메일 계정 확인
     */
    @Autowired
    private UsersRepository usersRepository;

    /**
     * 조직도 처리
     */
    @Autowired
    private EmlUserSupportService emlUserSupportService;

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

    @Autowired
    private EmlMailDao emlMessageDao;

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
     * 발송추적 처리
     */
    @Resource(name = "trackRepository")
    private TrackRepository trackRepository;

    @Resource(name = "logrepository")
    private TransportLogRepository transportLogRepository;

    /**
     * 메일 발송 큐 설정
     */
    @PostConstruct
    public void init() {
        this.queue = queueFactory.getQueue(MailQueueFactory.SPOOL);
    }


    public String sendMail(EmlMailFormVo emlMailFormVo) {

        List<MailImpl> mailList = null;
        try {
            // 수신확인 처리
            mailList = generateMail(emlMailFormVo);
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            try {
                sendSystemMessage(emlMailFormVo.getUserId(), emlMailFormVo.getMailFrom().get(0).getAddress(),
                        "[시스템 메일]메일 발송중 오류가 발생했습니다.", "-------------에러 메세지-------------<br><br>" + errors.toString(), null);
            } catch (MessagingException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (MailboxException e1) {
                e1.printStackTrace();
            }
            return "FAILURE";
        }
        try {
            enqueue(mailList);
        } catch (MailQueue.MailQueueException e) {
            e.printStackTrace();
        }
        try {
            postprocess(emlMailFormVo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "SUCCESS";
    }

    @Transactional("rest_tm")
    private void enqueue(List<MailImpl> mailList) throws MailQueue.MailQueueException {

        String groupId = null; //발송자 회사 고유번호
        for (MailImpl mail : mailList) {
            try {
                try {
                    mail.getMessage().writeTo(System.out);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                queue.enQueue(mail);
                trackRepository.logEvent(mail, "MT"); //발송 추적
                try {
                    groupId = (String) mail.getAttribute("groupid");
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
     * 메일 발송
     * @param emlMailFormVo 메일 발송용 vo
     * @return 성공 여부
     * @throws Exception
     */
    private void prepareMail(EmlMailFormVo emlMailFormVo) throws Exception {

        // 발신자 검사

        if (emlMailFormVo.getMailFrom() == null) {
			/*"발송자 이메일은 필수값입니다."*/
            throw new AppException(messageSource.getMessage("mail.desc.essentialSenderMailPlz", null, "",
                    LocaleContextHolder.getLocale()));
        }

        // 발신자 이름 검사

        String mailFromName = StringUtils.defaultString(emlMailFormVo.getMailFrom().get(0).getPersonal()); //보내는 사람 이름

        if (StringUtils.contains(mailFromName, "\"")) {
            //throw new AppException("보내는 이름에 입력할 수 없는 특수문자가 포함되어 있습니다.");
        }

        // 수신자 검사

        if (emlMailFormVo.getMailTo() == null || emlMailFormVo.getMailTo().size() == 0) {
			// "받는 사람이 정상적으로 지정되지 않았습니다."
            throw new AppException(messageSource.getMessage("mail.desc.notAssignReceiverPlz", null, "",
                    LocaleContextHolder.getLocale()));
        }

        // 제목 보정

        if (StringUtils.isEmpty(emlMailFormVo.getSubject())) {
			// "제목 없음"
            emlMailFormVo.setSubject(messageSource.getMessage("mail.label.noTitle", null, "",
                    LocaleContextHolder.getLocale()));
        }

        // 최근 수신자로 저장할 주소 추출
        // 사용자가 입력한 주소 그대로 최근 수신자로 추출

        List<String> mailToList = gatherRecentlyUsedRecipients(emlMailFormVo.getMailTo(), emlMailFormVo.getMailCc(), emlMailFormVo.getMailBcc());

        //최근 수신자 저장
        updateRecentlyUsedRecipient(emlMailFormVo.getUserId(), mailToList);


        //송신자 메일 용량 체크후 발송 여부 결정

/*
        EmlUserVo emlUserVo = emlUserService.selectMailQuotaInfo(emlMailFormVo.getUserId());
        if (emlUserVo.getRemainSize() <= 0) {
			// "메일함이 가득 찼습니다. 메일 쓰기를 사용할 수 없습니다."
            throw new AppException(messageSource.getMessage("mail.desc.mailboxFull", null, "",
                    LocaleContextHolder.getLocale()));
        }
*/

        String result = "SUCCESS";
        String mode = StringUtils.defaultIfEmpty(emlMailFormVo.getMode(), "write"); //작성 모드
        String massMailId = IdGenerator.generateUniqueId(); //대용량 메일 고유 번호

        // 대용량 첨부일 경우 EML_MASSIVE_MAIL, EML_MASSIVE_FILE, CMM_FILE 저장 (임시저장일 경우에는 대용량 첨부안함)

        if ("Y".equals(emlMailFormVo.getMassiveMailYn())) {
/*
            insertMassiveFile(emlMailFormVo.getFileList(), massMailId, emlMailFormVo.getFileDownEndDe());
            emlMailFormVo.setContent(getMassiveHtml(emlMailFormVo.getFileDownEndDe(), emlMailFormVo.getFileList())
                    + emlMailFormVo.getContent()); //대용량 메일 html 삽입
*/
        }

        String oriContent = emlMailFormVo.getContent();

        //보안메일일 경우
        if ("Y".equals(emlMailFormVo.getPasswordYn())) {
            //보안 메일일 경우 매크로(SEC_MAIL_ID) 치환
            oriContent = insertSecurityMail(emlMailFormVo);
        }

        //본문에 웹버그(CONFIRM_ID) URL 삽입
        String webBug = String.format(EmlCommonConstants.WEB_BUG, webBugUrl); //수신확인용 웹버그 URL
        emlMailFormVo.setContent(oriContent + webBug);
    }

    private void postprocess(EmlMailFormVo emlMailFormVo) throws Exception {

        String mode = emlMailFormVo.getMode();

        //MIME MESSAGE 객체 생성(첨부파일 포함)
        MimeMessage saveMessage = emlMessageUtilService.generateMime(emlMailFormVo, null);

//		if (MimeMessageUtil.getMessageSize(sendMessage) > mimeMaxSize * 1024 * 1024) {
//			throw new AppException("메일 용량이 " + mimeMaxSize + "MB를 초과했습니다.");
//		}

        //답장이나 전체답장일 경우 기존 메일에 답장 플래그 삽입
        if (StringUtils.endsWithIgnoreCase(mode, "reply")) {
            MultiValueMap multiMap = new MultiValueMap();
/*
            for (EmlMailKey mailPathVo : emlMailDao.selectMailPathByIds(Arrays.asList(emlMailFormVo.getMailIdList().split(",")))) {
                multiMap.put(mailPathVo.getMailboxName(), mailPathVo.getUid());
            }
            emlMailboxUtilService.setFlags(emlMailFormVo.getEmail(), multiMap, new Flags(Flags.Flag.ANSWERED), true);
*/
        } else if ("forward".equals(mode)) { //전달일 경우 기존 메일의 DB 전달 상태 변경
/*
            emlMailDao.updateMailForwardStatus(Arrays.asList(emlMailFormVo.getMailIdList().split(",")));
*/
        } else if ("temp".equals(mode) || "reserv".equals(mode)) { //임시보관함에서 발송한 경우, 예약 메일함 수정 화면에서 발송한 경우 기존 예약 메일 삭제
            //기존 메일 정보 완전 삭제
            List<String> mailIdList = null;//Arrays.asList(emlMailFormVo.getMailIdList().split(","));
            if (mailIdList != null && mailIdList.size() > 0) {
                MultiValueMap multiMap = new MultiValueMap();
/*
                for (EmlMailKey mailPathVo : emlMailDao.selectMailPathByIds(mailIdList)) {
                    multiMap.put(mailPathVo.getMailboxName(), mailPathVo.getUid());
                }
                emlMailboxUtilService.expungeWithoutBackup(emlMailFormVo.getEmail(), multiMap);
*/
            }
        }

        //보낸메일함에 저장
        saveMessage.setFlag(Flags.Flag.SEEN, true); //읽음 처리

        //웹버그 제거
        saveMessage = emlMessageUtilService.replaceMessageBody(saveMessage, EmlMailUtil.removeWebBug(emlMailFormVo.getContent()));

/*
        emlMailboxUtilService.appendMessage(emlMailFormVo.getEmail(), EmlCommonConstants.SENT_BOX, new MimeMessage[] {saveMessage}, false);
*/

        //대용량 메일일 경우 관리자가 목록으로 관리할 수 있도록 대용량 메일 정보를 따로 DB에 보관한다.
/*
        if ("Y".equals(emlMailFormVo.getMassiveMailYn())) {
            insertMassiveMailDetail(massMailId, emlMailFormVo.getCmpId(), sendMessage);
        }
*/

        // 일반발송 또는 개별발송에 따른 처리. 또한, 그에 따른 수신확인 레코드 생성
        // defaultLangCode 는 이메일주소를 텍스트로 장식할 때 사용한다.
        // 입력할 때 모두 결정해서 보내는 것이 맞기 때문에 언어 설정은 필요 없다.
    }

    private List<String> gatherRecentlyUsedRecipients(List<EmlMailFormToVo> to, List<EmlMailFormCcVo> cc, List<EmlMailFormBccVo> bcc) {
        List<String> addressList = new ArrayList<String>();
        for (EmlMailFormToVo emlAddressVo : to) {
            addressList.add(emlAddressVo.getAddress());
        }
        if (cc != null) {
            for (EmlMailFormCcVo emlAddressVo : cc) {
                addressList.add(emlAddressVo.getAddress());
            }
        }
        if (bcc != null) {
            for (EmlMailFormBccVo emlAddressVo : bcc) {
                addressList.add(emlAddressVo.getAddress());
            }
        }
        return addressList;
    }

    @Transactional("rest_tm")
    public String bookMail(EmlMailFormVo emlMailFormVo) throws Exception {

        // 예약 시각 검사

        if (emlMailFormVo.getReservDt() == null) {
            // "받는 사람이 정상적으로 지정되지 않았습니다."
            throw new AppException(messageSource.getMessage("mail.desc.notAssignReceiverPlz", null, "",
                    LocaleContextHolder.getLocale()));
        }

        // 발신자 검사

        if (emlMailFormVo.getMailFrom() == null) {
			/*"발송자 이메일은 필수값입니다."*/
            throw new AppException(messageSource.getMessage("mail.desc.essentialSenderMailPlz", null, "",
                    LocaleContextHolder.getLocale()));
        }

        // 발신자 이름 검사

        String mailFromName = StringUtils.defaultString(emlMailFormVo.getMailFrom().get(0).getPersonal()); //보내는 사람 이름

        if (StringUtils.contains(mailFromName, "\"")) {
            //throw new AppException("보내는 이름에 입력할 수 없는 특수문자가 포함되어 있습니다.");
        }

        // 수신자 검사

        if (emlMailFormVo.getMailTo() == null || emlMailFormVo.getMailTo().size() == 0) {
            // "받는 사람이 정상적으로 지정되지 않았습니다."
            throw new AppException(messageSource.getMessage("mail.desc.notAssignReceiverPlz", null, "",
                    LocaleContextHolder.getLocale()));
        }

        // 제목 보정

        if (StringUtils.isEmpty(emlMailFormVo.getSubject())) {
            // "제목 없음"
            emlMailFormVo.setSubject(messageSource.getMessage("mail.label.noTitle", null, "",
                    LocaleContextHolder.getLocale()));
        }

        String result = "SUCCESS";
        String mode = StringUtils.defaultIfEmpty(emlMailFormVo.getMode(), "write"); //작성 모드
        String massMailId = IdGenerator.generateUniqueId(); //대용량 메일 고유 번호

        // 최근 수신자로 저장할 주소 추출
        // 사용자가 입력한 주소 그대로 최근 수신자로 추출

        List<String> mailToList = gatherRecentlyUsedRecipients(emlMailFormVo.getMailTo(), emlMailFormVo.getMailCc(), emlMailFormVo.getMailBcc());

        //최근 수신자 저장
        updateRecentlyUsedRecipient(emlMailFormVo.getUserId(), mailToList);

        //예약 메일 등록/수정 모드일 경우

        //기존 메일 정보 완전 삭제
        List<EmlMailKeyVo> emlMailKeyVoList = emlMailFormVo.getMailIdList();
        if (emlMailKeyVoList != null) {
            MultiValueMap multiMap = new MultiValueMap();
            for (EmlMailKeyVo emlMailKeyVo : emlMailKeyVoList) {
                multiMap.put(emlMailKeyVo.getMailboxName(), emlMailKeyVo.getUid());
            }
            // 로그인용 주소, 메일박스, uid 목록
            emlMailboxUtilService.expungeWithoutBackup(emlMailFormVo.getUserId(), multiMap);
        }

        //예약 메일함에 저장
        emlMailFormDao.insert(new EmlMailForm(emlMailFormVo));

        return null; //result;
    }

    /**
     * 메일 발송할 객체를 수신자별로 분리하고 수신확인 정보 입력
     *
     * @return MailImpl 메일 정보
     * @throws Exception
     */
	@Transactional("rest_tm")
    private List<MailImpl> generateMail(EmlMailFormVo emlMailFormVo) throws Exception {
        // 대용량 첨부 처리
        // 보안 메일 처리
        prepareMail(emlMailFormVo);

        List<MailImpl> mailList = new ArrayList<MailImpl>();

        if (!"Y".equals(emlMailFormVo.getSeparateSendYn())) { //일반 발송
            mailList.addAll(internalGenerateMail(emlMailFormVo));
        } else { // 개별 발송
            List<EmlMailFormToVo> to = emlMailFormVo.getMailTo();

            for (EmlMailFormToVo emlAddressVo : to) {
                List<EmlMailFormToVo> newTo = new ArrayList<EmlMailFormToVo>();
                newTo.add(emlAddressVo);
                emlMailFormVo.setMailTo(newTo);
                mailList.addAll(internalGenerateMail(emlMailFormVo));
            }
        }
        return mailList;
    }

    private List<MailImpl> internalGenerateMail(EmlMailFormVo emlMailFormVo) throws Exception {

        // 일반 또는 개별 발송 처리: To 헤더 작성방법이 다름. 일반 발송은 CC 와 BCC 에게 한 카피만 보냄. 개별 발송은 CC 와 BCC 에게 To 의 수신자수 만큼 보냄.

        List<MailImpl> mailList = new ArrayList<MailImpl>();

        // 메일 송신자
        MailAddress sender = new MailAddress(emlMailFormVo.getMailFrom().get(0).getAddress());

        // 메일 수신자
        List<MailAddress> recipients;

        List<String> to = new ArrayList<String>();
        for (EmlMailFormToVo emlMailFormToVo : emlMailFormVo.getMailTo()) {
            to.add(emlMailFormToVo.getAddress());
        }
        for (EmlMailFormCcVo emlMailFormCcVo : emlMailFormVo.getMailCc()) {
            to.add(emlMailFormCcVo.getAddress());
        }
        for (EmlMailFormBccVo emlMailFormBccVo : emlMailFormVo.getMailBcc()) {
            to.add(emlMailFormBccVo.getAddress());
        }

        for (String address : to) {

            // 읽음 확인 아이디 생성

            String readId = IdGenerator.generateUniqueId();

            // To 에 모두 기술하는 형태의 마임 생성

            MimeMessage message = emlMessageUtilService.generateMime(emlMailFormVo, readId);

            // Mail 객체 생성

            recipients = new ArrayList<MailAddress>();
            recipients.add(new MailAddress(address));

            MailImpl mail = new MailImpl(MailImpl.getId(), sender, recipients, new MimeMessageCopyOnWriteProxy(message));
            mail.setAttribute("groupid", "G1");
            mailList.add(mail);
        }

        return mailList;
    }

    /**
     * 메일 발송전에 수신확인 정보 입력한다.
     * @param confirmMailId 수신확인 부모 아이디
     * @param sender 발송자 메일
     * @param content 메일 내용
     * @param recvList 모든 수신자 이메일 주소 목록
     * @param message 메일 mime
     * @param emailId 이메일 아이디
     * @param deptId 대표 부서 아이디
     * @param cmpId 발송자 회사 아이디
     * @param orgEmailList 내부 사용자 이메일 목록
     * @return 발송할 메일 객체
     * @throws javax.mail.MessagingException e1
     * @throws java.io.IOException e2
     */
//	@Transactional("rest_tm")
    private List<MailImpl> insertReadConfirmDetail(String confirmMailId, MailAddress sender,
                                                   String content, Map<String, InternetAddress> recvList, MimeMessage message, String emailId,
                                                   String deptId, String cmpId, Map<String, String> orgEmailList) throws MessagingException, IOException {
        List<MailImpl> mailList = new ArrayList<MailImpl>();
        if (recvList == null) {
            return mailList;
        }
        MimeMessage newMimeMessage = null;
        String inMsgId = ""; //내부메일 메세지 아이디
        List<MailAddress> inRecv = new ArrayList<MailAddress>(); //내부 수신자 목록
        Iterator<String> keys = recvList.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (orgEmailList.containsKey(key)) {
                inRecv.add(new MailAddress(key));
            }
        }

        //내부 메일 마임 싱글톤화를 위한 처리 시작
        if (inRecv.size() > 0) {
            newMimeMessage = new MimeMessage(message);
            newMimeMessage.removeHeader("X-ApproveGroupId"); //내부메일은 승인메일 필터에 걸리지 않는다.
            emlMessageUtilService.replaceMessageBody(newMimeMessage, EmlMailUtil.removeWebBug(content));

            newMimeMessage = new MimeMessage(newMimeMessage);

            String key = UUID.randomUUID().toString();
            InputStream headersIs = new InternetHeadersInputStream(newMimeMessage.getMatchingHeaderLines(
                    new String[] {"Content-Type", "Content-Transfer-Encoding"}));
            SequenceInputStream contentIs = new SequenceInputStream(headersIs, newMimeMessage.getRawInputStream());

            OutputStream os = singletonRepository.put(key);
            try {
                IOUtils.copy(contentIs, os);
            } finally {
                if (contentIs != null) {
                    try {
                        contentIs.close();
                    } catch (IOException e) {

                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {

                    }
                }
            }

            newMimeMessage.addHeader("X-SourceID", key);

            // 마임 싱글톤화를 위한 처리 종료
            if (StringUtils.isNotEmpty(confirmMailId)) { //수신확인 부모 테이블에 입력된 경우에만 셋팅
                newMimeMessage.setHeader(EmlCommonConstants.RECV_MASTER_CUSTOM_HEADER, confirmMailId);
            }

            inMsgId = newMimeMessage.getMessageID();

            MailImpl mail = new MailImpl(MailImpl.getId(), sender, inRecv,
                    new MimeMessageCopyOnWriteProxy(newMimeMessage));
            if (StringUtils.isEmpty(confirmMailId)) { //수신확인 데이터가 입력되어 있지 않으면 승인메일 필터 적용안하게 처리
                mail.setAttribute("com.oreumio.Approved", "Y");
            }

            mail.setAttribute("userid", emailId);
            mail.setAttribute("departmentid", deptId);
            mail.setAttribute("companyid", cmpId);
            mail.setAttribute("content", key);
            mailList.add(mail);
        }

        for (Map.Entry<String, InternetAddress> entry : recvList.entrySet()) {
            newMimeMessage = new MimeMessage(message);
            String address = entry.getKey();
            InternetAddress internetAddress = entry.getValue();
            boolean inMailYn = orgEmailList.containsKey(address);
            if (!inMailYn) { //외부 메일
                newMimeMessage.removeHeader("X-From"); //외부 사람은 볼 필요가 없는 보낸 사람 조직도 이름 지움
                newMimeMessage.removeHeader("Received"); //sendMail 메소드에서 미리 셋팅해놓음(내부발송할 때만 남겨놓고 외부메일에서는 지운다)
            }
            newMimeMessage.saveChanges();
            if (StringUtils.isNotEmpty(confirmMailId)) { //수신확인 부모 테이블에 입력된 경우에만 수신확인 자식 데이터 입력
                String confirmId = IdGenerator.generateUniqueId();
                //내부메일은 본문에 웹버그 추가하지 않고 수신확인 커스텀 헤더 추가 X-Disposition-Notification-To
                if (!inMailYn) { //외부 메일
                    //수신확인 웹버그 매크로(CONFIRM_ID) 치환
                    newMimeMessage = emlMessageUtilService.replaceMessageBody(newMimeMessage,
                            StringUtils.replace(content, "##**CONFIRM_ID**##", confirmId));
                }

                //수신확인 상세 테이블 입력
                EmlReadConfirm confirm = new EmlReadConfirm();
                confirm.setConfirmId(confirmId);
                confirm.setConfirmMailId(confirmMailId);
                confirm.setMailTo(MimeUtility.decodeText(internetAddress.toString()));
                if (!inMailYn) { //외부 메일
                    confirm.setMsgId(newMimeMessage.getMessageID());
                } else { //내부메일
                    confirm.setMsgId(inMsgId);
                    confirm.setMailToEmailId(orgEmailList.get(address)); //받는 사람 이메일 아이디
                }
                confirm.setMailToType(!inMailYn ? "E" : "I"); //E:외부 사용자, I:내부 사용자
                confirm.setProcStatus("N"); //정상
                confirm.setReadCnt(0);
                emlReadConfrimDao.persist(confirm);
            }

            if (!inMailYn) { //외부 메일
                List<MailAddress> recv = new ArrayList<MailAddress>();
                recv.add(new MailAddress(address));
                MailImpl mail = new MailImpl(MailImpl.getId(), sender, recv, newMimeMessage);
                if (StringUtils.isEmpty(confirmMailId)) { //수신확인 데이터가 입력되어 있지 않으면 승인메일 필터 적용안하게 처리
                    mail.setAttribute("com.newriseup.Approved", "Y");
                }
                mail.setAttribute("userid", emailId);
                mail.setAttribute("departmentid", deptId);
                mail.setAttribute("companyid", cmpId);

                mailList.add(mail);
            }
        }
        return mailList;
    }

    /**
     * 수신확인 부모 테이블 입력한다.
     * @param emailId 이메일 아이디
     * @param msgId 보낸 메일함에 저장된 메일 메세지 아이디
     * @param subject 제목
     * @param dispMailTo 표시용 받는 사람
     * @param mailReadRecptnYn 수신확인 알림 메일 받기 여부
     * @param rcverCnt 수신자 수
     * @param mailSendDt 보낸 날짜
     * @param mailToCnt 메일 받는 사람수 - 1
     * @return 수신확인 부모 아이디
     */
//	@Transactional("rest_tm")
    private String insertReadConfirmMail(String emailId, String msgId, String subject, String dispMailTo,
                                         String mailReadRecptnYn, int rcverCnt, Date mailSendDt, short mailToCnt) {
        String confirmMailId = IdGenerator.generateUniqueId();
        EmlReadConfirmMail confirmMail = new EmlReadConfirmMail();
        confirmMail.setConfirmMailId(confirmMailId);
        confirmMail.setSubject(subject);
        confirmMail.setDispMailTo(dispMailTo);
        confirmMail.setEmailId(emailId);
        confirmMail.setMailReadRecptnYn(StringUtils.defaultIfBlank(mailReadRecptnYn, "N"));
        confirmMail.setMsgId(msgId);
        confirmMail.setRcverCnt(rcverCnt);
        confirmMail.setReadCnt(0);
        confirmMail.setRegDt(new Date());
        confirmMail.setMailSendDt(mailSendDt);
        confirmMail.setMailToCnt(mailToCnt);
        emlReadConfrimMailDao.persistSimple(confirmMail);
        return confirmMailId;
    }

    /**
     * 대용량 파일 정보 입력
     * @param fileList 파일 정보 목록
     * @param massMailId 대용량 메일 아이디
     * @param fileDownEndDe 대용량 파일 다운로드 종료 일자
     * @throws java.io.IOException
     */
    @Transactional("rest_tm")
    private void insertMassiveFile(List<EmlFile> fileList, String massMailId,
                                   String fileDownEndDe) throws IOException {
        if (fileList == null || fileList.size() == 0) {
            return;
        }

        //EML_MASSIVE_MAIL 입력
        EmlMassiveMail emlMassiveMail = new EmlMassiveMail();
        emlMassiveMail.setMassMailId(massMailId);
        String endDt = fileDownEndDe.split("GMT")[0] + "235959GMT" + fileDownEndDe.split("GMT")[1];
        emlMassiveMail.setFileDownEndDt(DateUtil.stringToDate(endDt));
        //EML_MASSIVE_FILE 입력
        for (EmlFile file : fileList) {
            file.setFileId(IdGenerator.generateUniqueId());
            EmlMassiveFile emlMassiveFile = new EmlMassiveFile();
            emlMassiveFile.setFileId(file.getFileId());
            emlMassiveMail.addEmlMassiveFile(emlMassiveFile);
        }
/*
        emlFileDao.insertFileList(massMailId, fileList);
*/
        emlMassiveMailDao.insert(emlMassiveMail);
    }

    /**
     * 대용량 첨부 다운로드 영역 html
     * @param fileDownEndDe 다운로드 만료 일자
     * @param fileList 첨부파일 목록
     * @return html
     */
    public String getMassiveHtml(String fileDownEndDe, List<EmlFile> fileList) {
        StringBuilder str = new StringBuilder();
        str.append("<div style=\"margin: 0px 0px 30px; text-decoration: none; color: #333;");
        str.append(" font-size: 12px; font-family: sans-serif; \">");
        String timeZone = "";
        if (fileList == null || fileList.size() == 0) {
            return "";
        }
        if (StringUtils.isEmpty(fileDownEndDe)) {
            fileDownEndDe = messageSource.getMessage("mail.label.unlimit", /*무제한*/
                    null, "", LocaleContextHolder.getLocale());
        } else {
            timeZone = "GMT" + fileDownEndDe.split("GMT")[1];
            fileDownEndDe = fileDownEndDe.substring(0, 4) + "." + fileDownEndDe.substring(4, 6)
                    + "." + fileDownEndDe.substring(6, 8);
        }
        str.append("<div style=\"position: relative; height: 34px; line-height: 34px; padding: 0 0 0 10px;");
        str.append(" border-style: solid; border-color: #cccccc; border-width: 1px 1px 0;");
        str.append(" -webkit-border-radius: 3px 3px 0 0; border-radius: 3px 3px 0 0;\">");
        str.append("<strong>");
        str.append(messageSource.getMessage("mail.label.highCapacityAttach",
                null, "", LocaleContextHolder.getLocale()));	/*대용량 첨부*/
        str.append("</strong>");
        str.append(" - ");
        str.append(messageSource.getMessage("mail.label.downloadPeriod",
                null, "", LocaleContextHolder.getLocale()));	/*다운로드 기한*/
        str.append(" : ");
        str.append(fileDownEndDe);
        if (StringUtils.isNotEmpty(timeZone)) {
            str.append(" (" + timeZone + ")");
        }
        str.append("</div>");
        str.append("<ul style=\"list-style: none; min-height: 27px; max-height: 100%; margin: 0;");
        str.append(" padding: 10px 0 10px; border-style: solid; border-color: #cccccc; border-width: 1px;");
        str.append(" -webkit-border-radius: 0 0 3px 3px; border-radius: 0 0 3px 3px;\">");
        for (int i = 0; i < fileList.size(); i++) {
            EmlFile file = fileList.get(i);
            str.append("<li style=\"padding: 0 0 0 10px; height: 22px; line-height: 22px;\">");
            str.append("<a href=\"" + webBugUrl);
            str.append("/data/emlMail/emlMassiveFileDownload.do?fileId=" + file.getFileId() + "\" target=\"blank\"");
            str.append(" style=\"text-decoration: none; color: #333;\">");
            str.append("<span style=\"margin: -4px 5px 0 0; display: inline-block; width: 16px; height: 16px;");
            str.append(" background-image: url(");
            str.append(imageServerUrl).append("/img/icon_file.png); background-repeat: no-repeat;");
            str.append(" background-position: left " + FileUtil.getImageIcon(file.getFileName()));
            str.append("px; vertical-align: middle;\">");
            str.append("</span>");
            str.append(file.getFileName() + "</a>");
            str.append("<span style=\"margin: 0 0 0 10px; color: #777;\">");
            str.append(FileUtil.strNumToFileSize(file.getFileSize()));
            str.append("</span>");
            str.append("</li>");
        }
        str.append("</ul></div>");
        return str.toString();
    }

    /**
     * 보안 메일 정보 등록
     * @param emlMailFormVo 메일 등록 폼 vo
     * @return secHtml 보안 메일 html
     * @throws IOException
     * @throws javax.mail.MessagingException
     * @throws java.text.ParseException
     */
    @Transactional("rest_tm")
    public String insertSecurityMail(EmlMailFormVo emlMailFormVo) throws IOException,
            MessagingException, ParseException {
        EmlMailFormVo emlSecVo = new EmlMailFormVo();
        BeanUtils.copyProperties(emlMailFormVo, emlSecVo);

        //원문
        MimeMessage secMessage = emlMessageUtilService.generateMime(emlSecVo, null);
        secMessage.removeHeader("X-password"); //임시저장 수정시에만 필요
        String secMailId = insertSecurityMail(emlMailFormVo, secMessage);

        //보안 메일 매크로(SEC_MAIL_ID) 치환
        String secHtml = String.format(EmlMailUtil.getSecHtml(webBugUrl, messageSource), secMailId);
        return secHtml;
    }

    public String insertSecurityMail(EmlMailFormVo emlMailFormVo, MimeMessage secMessage) throws IOException,
            MessagingException, ParseException {
        return "";
    }

    /**
     * 대용량 메일 상세 정보 등록
     * @param massMailId 대용량 메일 고유 번호
     * @param groupId 그룹 고유번호
     * @param mime 메일 mime
     * @throws IOException
     * @throws MessagingException
     */
    @Transactional("rest_tm")
    public void insertMassiveMailDetail(String massMailId, String groupId, MimeMessage mime) throws IOException,
            MessagingException {
        MimeMessageParsingHelper messageProxy = MimeMessageParsingHelper.getMimeMessageParsingProxy(
                mime, null);
        EmlMassiveMailDetail emlMassiveMailDetail = new EmlMassiveMailDetail();
        emlMassiveMailDetail.setMassMailId(massMailId);
        emlMassiveMailDetail.setSubject(messageProxy.getSubject());
        emlMassiveMailDetail.setDispMailFrom(messageProxy.getSenderDisp());
        emlMassiveMailDetail.setMailSize(messageProxy.getSize());
        emlMassiveMailDetail.setImportYn(messageProxy.isPrioritized() ? "Y" : "N");
        emlMassiveMailDetail.setSecMailYn(messageProxy.isSecured() ? "Y" : "N");
        emlMassiveMailDetail.setMailSendDt(messageProxy.getSentDate());
        emlMassiveMailDetail.setDelYn("N");
        emlMassiveMailDetail.setClientId(groupId);
        String saveDir = FileUtil.createTempSaveDir(uploadTempDir, "eml");
        String fileName = FileUtil.getValidFileName(StringUtils.defaultIfBlank(messageProxy.getSubject(), "UNTITLED"), 50) + ".eml";
        String savedFileName = IdGenerator.generateUniqueId() + ".eml";
        File file = new File(saveDir, savedFileName);
        FileUtils.copyInputStreamToFile(new MimeMessageInputStream(mime), file);

        EmlFile massFile = new EmlFile();
        massFile.setGroupId(groupId);
        massFile.setFileGrpId(massMailId);
        massFile.setFileName(fileName);
        massFile.setFilePath(FileUtil.relative(new File(uploadTempDir), new File(saveDir)));
        massFile.setFileSize(file.length());
        massFile.setFileType("message/rfc822");
        massFile.setModule("eml");
        massFile.setSaveFileName(savedFileName);
        massFile.setFileId(emlFileDao.insert(massFile));

        emlMassiveMailDetail.setFileId(massFile.getFileId());
        emlMassiveMailDetailDao.insert(emlMassiveMailDetail);
    }

    private void updateRecentlyUsedRecipient(String userId, List<String> mailToList) {
        if (mailToList == null || mailToList.size() == 0) {
            return;
        }

        // 기존 주소는 사용된 시각을 변경

        List<EmlRecentlyUsedRecipient> emlRecentlyUsedRecipientList = emlRecentlyUsedRecipientDao.listRecentlyUsedRecipients(userId);
        if (emlRecentlyUsedRecipientList != null) {
            for (EmlRecentlyUsedRecipient emlRecentlyUsedRecipient : emlRecentlyUsedRecipientList) {
                // 기존에 이미 있으면 날짜만 변경
                if (mailToList.contains(emlRecentlyUsedRecipient.getMailTo())) {
                    emlRecentlyUsedRecipient.setRegDt(new Date());
                    mailToList.remove(emlRecentlyUsedRecipient.getMailTo());
                }
            }
        }

        // 새로운 주소를 추가

        for (String mailTo : mailToList) {
            //간단한 이메일 주소 체크
            if (mailTo.indexOf("@") == -1) {
                continue;
            }

            EmlRecentlyUsedRecipient emlRecentlyUsedRecipient = new EmlRecentlyUsedRecipient();
            emlRecentlyUsedRecipient.setId(IdGenerator.generateUniqueId());
            emlRecentlyUsedRecipient.setUserId(userId);
            emlRecentlyUsedRecipient.setMailTo(mailTo);
            emlRecentlyUsedRecipientDao.persist(emlRecentlyUsedRecipient);
        }

        // 100 개 뒤의 주소는 삭제

        emlRecentlyUsedRecipientList = emlRecentlyUsedRecipientDao.listRecentlyUsedRecipients(userId);
        if (emlRecentlyUsedRecipientList != null) {
            for (int i = 100; i < emlRecentlyUsedRecipientList.size(); i++) {
                emlRecentlyUsedRecipientDao.delete(emlRecentlyUsedRecipientList.get(i));
            }
        }
    }

    /**
     * 회사 도메인으로 발송시 없는 계정이면 시스템 메일을 받은메일함에 저장시킴
     * @param from 보낸 사람의 이메일 주소(메인 계정)
     * @param message 보낸 메일의 mime
     * @param invalidAddrList 잘못된 내부 메일 주소 목록
     * @param exceedQuotaAddrList 메일함 용량이 남아 있지 않은 수신자 메일 주소 목록
     * @throws javax.mail.MessagingException
     * @throws org.apache.james.mailbox.exception.MailboxException
     * @throws java.io.IOException
     * @throws java.text.ParseException
     * @throws org.apache.james.mime4j.field.datetime.parser.ParseException
     */
    private void sendInvalidUserMail(String from, MimeMessage message, List<String> invalidAddrList,
                                     List<String> exceedQuotaAddrList)
            throws MessagingException, IOException, MailboxException, ParseException,
            org.apache.james.mime4j.field.datetime.parser.ParseException {
        if (invalidAddrList.size() == 0 && exceedQuotaAddrList.size() == 0) {
            return;
        }
        String[] langCodes = message.getContentLanguage();
        String langCode = langCodes != null ? langCodes[0] : defaultLangCode;
        Locale locale = new Locale(langCode);
        String existSubject = MimeUtility.decodeText(StringUtils.defaultString(message.getSubject()));
        DateTime dt = new DateTimeParser(new StringReader(message.getHeader("Date")[0])).parseAll();
        String timeZone = "";
        if (dt.getTimeZone() < 0) {
            timeZone = "-" + StringUtils.leftPad(Integer.toString(dt.getTimeZone() * -1), 4, "0");
        } else {
            timeZone = "+" + StringUtils.leftPad(Integer.toString(dt.getTimeZone()), 4, "0");
        }
        MailAddress mailAddr = new MailAddress(from);
        EmlMailFormVo emlMailFormVo = new EmlMailFormVo();
		/*"(시스템메일)메일 발송 결과 보고입니다."*/
        emlMailFormVo.setSubject(messageSource.getMessage("mail.desc.systemMailSendResultReportPlz", null, "", locale));
        emlMailFormVo.setPriority("3");
/*
        List<EmlMailFormFromVo> from = new ArrayList<EmlMailFormFromVo>();
        from.add(new EmlMailFormFromVo("postmaster@" + mailAddr.getDomain()));
        emlMailFormVo.setMailFrom(from);
*/
/*
		emlMailFormVo.setMailTos(from);
*/
        StringBuffer content = new StringBuffer();
		/*"(시스템메일)메일 발송 결과 보고입니다."*/
        content.append(messageSource.getMessage("mail.desc.systemMailSendResultReportPlz", null, "", locale));
        content.append("<br/><br/>");
		/*메일 발송 중 오류가 발생하여 아래의 수신자들에게는 메일이 발송되지 않았습니다.*/
        content.append(messageSource.getMessage("mail.desc.notSendMailToReceiverCauseSendingErrorPlz", null, "", locale));
        content.append("<br/><br/>");
		/*아래*/
        content.append("--- ").append(messageSource.getMessage("mail.desc.downContents", null, "", locale)).append(" ---");
        content.append("<br/><br/>");
		/*제목: */
        content.append(messageSource.getMessage("mail.title.subject", null, "", locale)).append(": ");
        content.append(existSubject).append("<br/>");
        //content.append("보낸 날짜: ").append(message.getHeader("Date")[0]);
		/*보낸 날짜: */
        content.append(messageSource.getMessage("mail.label.sentDate", null, "", locale)).append(": ");
        content.append(dt.getYear() + "-"
                + StringUtils.leftPad(Integer.toString(dt.getMonth()), 2, "0") + "-"
                + StringUtils.leftPad(Integer.toString(dt.getDay()), 2, "0") + " "
                + StringUtils.leftPad(Integer.toString(dt.getHour()), 2, "0") + ":"
                + StringUtils.leftPad(Integer.toString(dt.getMinute()), 2, "0") + "."
                + StringUtils.leftPad(Integer.toString(dt.getSecond()), 2, "0")
                + " GMT" + timeZone).append("<br/>");
        if (invalidAddrList.size() > 0) {
            content.append("<br/>");
			/*존재하지 않거나 사용 중지중인 사용자 목록:*/
            content.append(messageSource.getMessage("mail.desc.notExistAndNotUseUserList", null, "", locale))
                    .append(":").append("<br/>");
        }
        for (String addr : invalidAddrList) {
            content.append("- ").append(addr.replace("<", "&lt;").replace(">", "&gt;")).append("<br/>");
        }
        if (exceedQuotaAddrList.size() > 0) {
            content.append("<br/>");
			/*수신자의 메일함 용량이 남아있지 않은 사용자 목록:*/
            content.append(messageSource.getMessage("mail.desc.receiverMailboxEmptyCapacityUserList", null, "", locale))
                    .append(":").append("<br/>");
        }
        for (String addr : exceedQuotaAddrList) {
            content.append("- ").append(addr.replace("<", "&lt;").replace(">", "&gt;")).append("<br/>");
        }
        emlMailFormVo.setContent(content.toString());
        MimeMessage sysMessage = emlMessageUtilService.generateMime(emlMailFormVo, null);
        emlMailboxUtilService.appendMessage(from, EmlCommonConstants.RECV_BOX, new MimeMessage[]{sysMessage}, true);
    }

    /**
     * 발송자의 받은 메일함에 시스템 메일을 저장한다.
     * @param userId 받는 사람 이메일 아이디
     * @param recipientAddress 보낸 사람
     * @param subject 제목
     * @param content 내용
     * @param fileList 첨부 파일
     * @throws java.text.ParseException
     * @throws javax.mail.MessagingException
     * @throws org.apache.james.mailbox.exception.MailboxException
     * @throws java.io.IOException
     */
    private void sendSystemMessage(String userId, String recipientAddress, String subject, String content, List<EmlFile> fileList)
            throws MessagingException, ParseException, IOException, MailboxException {
        EmlMailFormVo emlMailFormVo = new EmlMailFormVo();
        MailAddress recipient = new MailAddress(recipientAddress);
        emlMailFormVo.setSubject(subject);
        emlMailFormVo.setPriority("3");
        List<EmlMailFormFromVo> from = new ArrayList<EmlMailFormFromVo>();
        from.add(new EmlMailFormFromVo("postmaster@" + recipient.getDomain()));
        emlMailFormVo.setMailFrom(from);
/*
		emlMailFormVo.setMailTos(from);
		emlMailFormVo.setFileList(fileList);
*/
        emlMailFormVo.setContent(content);
        MimeMessage message = emlMessageUtilService.generateMime(emlMailFormVo, null);
        emlMailboxUtilService.appendMessage(userId, EmlCommonConstants.RECV_BOX, new MimeMessage[]{message}, true);
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
                                                       List<String> exceedQuotaAddrList, String langCode, Map<String, String> orgEmailList) throws RecipientRewriteTable.ErrorMappingException,
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
}
