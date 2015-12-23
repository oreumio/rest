package com.oreumio.james.rest.form;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 메일 발송 데이터가 저장될 vo
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_MAIL_FORM")
public class EmlMailForm implements Serializable {

	private static final long serialVersionUID = 4397779894496831642L;

    @Id
    @Column(name = "MAIL_FORM_ID", nullable = false, length = 30)
    private String id;

    /**
	 * 발송자 이메일 아이디
	 */
    @Column(name = "USER_ID", nullable = false, length = 30)
	private String userId;

	/**
	 * 제목
	 */
    @Column(name = "SUBJECT")
	private String subject;

	/**
	 * 중요도
	 */
    @Column(name = "PRIORITY", length = 1)
	private String priority;

	/**
	 *  개별발송 여부. 
	 */
    @Column(name = "SEPARATE", length = 1)
	private String separateSendYn;

	/** 대용량 메일 여부. */
    @Column(name = "BIG_ATTACHMENT", length = 1)
	private String massiveMailYn;

	/** 비밀번호. */
    @Column(name = "PASSWORD", length = 10)
	private String password;

	/** 보안메일 사용여부. */
    @Column(name = "SECURED", length = 1)
	private String passwordYn;

	/** 메일 본문 내용. */
    @Column(name = "CONTENT", length = 3000)
	private String content;

    @OneToMany(mappedBy = "mailForm", cascade = CascadeType.ALL)
	/** 보내는 사람(이메일 주소). */
	private List<EmlMailFormFrom> mailFrom;

	/** 메일 to. */
    @OneToMany(mappedBy = "mailForm", cascade = CascadeType.ALL)
	private List<EmlMailFormTo> mailTo;

	/** 메일 cc. */
    @OneToMany(mappedBy = "mailForm", cascade = CascadeType.ALL)
	private List<EmlMailFormCc> mailCc;

	/** 메일 bcc. */
    @OneToMany(mappedBy = "mailForm", cascade = CascadeType.ALL)
	private List<EmlMailFormBcc> mailBcc;

	/** 첨부파일 목록. */
/*
	private List<EmlFile> fileList;
*/

	/** 대용량 메일 첨부파일 다운로드 기한. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FILE_DOWNLOAD_EXP")
	private Date fileDownEndDe;

	/**
	 * 메일 쓰기 모드
	 * 쓰기:write,답변:reply,모두답변:allReply,전달:forward
	 * 재발송:resend,임시저장:temp,예약메일:reserv
	 */
    @Column(name = "MODE")
	private String mode;

	/** 메일 전송후 이동할 페이지 코드값. */
    @Column(name = "AFTER_PAGE")
	private String afterPage;

	/** 예약날짜(2013-12-04). */
    @Temporal(TemporalType.DATE)
    @Column(name = "SEND_DATE")
	private Date reservDt;

    /** 예약메일 기준시간 정보(GMT+09:00). */
    @Column(name = "TIMEZONE")
    private String timeZoneId;

    /**
     * 예약메일, 임시보관함 메일의 수정전 메일이나 전달시 기존 메일 정보
     */
    @Column(name = "MAIL_LIST")
    private String mailIdList;

    /** 
     * 보안 메일 조회 만료 기한.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "SECURITY_EXP")
	private Date storeExpireDe;

	/**
	 * 수신확인 응답 메일 받기 여부
	 */
    @Column(name = "MDN_REQUEST", length = 1)
	private String mailReadRecpinYn;

	/**
	 * 다국어 코드
	 */
    @Column(name = "LANG_CODE", length = 10)
	private String langCd;

	/**
	 * 발송자 아이피
	 */
    @Column(name = "REMOTE_ADDRESS", length = 30)
	private String remoteAddr;

    public EmlMailForm() {
    }

    public EmlMailForm(EmlMailFormVo emlMailFormVo) {
        userId = emlMailFormVo.getUserId();
        subject = emlMailFormVo.getSubject();
        priority = emlMailFormVo.getPriority();
        separateSendYn = emlMailFormVo.getSeparateSendYn();
        massiveMailYn = emlMailFormVo.getMassiveMailYn();
        password = emlMailFormVo.getPassword();
        passwordYn = emlMailFormVo.getPasswordYn();
        content = emlMailFormVo.getContent();
        mailFrom = new ArrayList<EmlMailFormFrom>();
        for (EmlMailFormFromVo emlAddressVo : emlMailFormVo.getMailFrom()) {
            EmlMailFormFrom emlMailFormFrom = new EmlMailFormFrom(emlAddressVo);
            emlMailFormFrom.setMailForm(this);
            mailFrom.add(emlMailFormFrom);

        }
        mailTo = new ArrayList<EmlMailFormTo>();
        for (EmlMailFormToVo emlAddressVo : emlMailFormVo.getMailTo()) {
            EmlMailFormTo emlMailFormTo = new EmlMailFormTo(emlAddressVo);
            emlMailFormTo.setMailForm(this);
            mailTo.add(emlMailFormTo);
        }
        mailCc = new ArrayList<EmlMailFormCc>();
        for (EmlMailFormCcVo emlAddressVo : emlMailFormVo.getMailCc()) {
            EmlMailFormCc emlMailFormCc = new EmlMailFormCc(emlAddressVo);
            emlMailFormCc.setMailForm(this);
            mailCc.add(emlMailFormCc);
        }
        mailBcc = new ArrayList<EmlMailFormBcc>();
        for (EmlMailFormBccVo emlAddressVo : emlMailFormVo.getMailBcc()) {
            EmlMailFormBcc emlMailFormBcc = new EmlMailFormBcc(emlAddressVo);
            emlMailFormBcc.setMailForm(this);
            mailBcc.add(emlMailFormBcc);
        }
//        fileList = null;
        fileDownEndDe = emlMailFormVo.getFileDownEndDe();
        mode = emlMailFormVo.getMode();
        afterPage = emlMailFormVo.getAfterPage();
        reservDt = emlMailFormVo.getReservDt();
        timeZoneId = emlMailFormVo.getTimeZoneId();
        mailIdList = null;
        storeExpireDe = emlMailFormVo.getStoreExpireDe();
        mailReadRecpinYn = emlMailFormVo.getMailReadRecpinYn();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the separateSendYn
	 */
	public String getSeparateSendYn() {
		return separateSendYn;
	}

	/**
	 * @param separateSendYn the separateSendYn to set
	 */
	public void setSeparateSendYn(String separateSendYn) {
		this.separateSendYn = separateSendYn;
	}

	/**
	 * @return the massiveMailYn
	 */
	public String getMassiveMailYn() {
		return massiveMailYn;
	}

	/**
	 * @param massiveMailYn the massiveMailYn to set
	 */
	public void setMassiveMailYn(String massiveMailYn) {
		this.massiveMailYn = massiveMailYn;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the passwordYn
	 */
	public String getPasswordYn() {
		return passwordYn;
	}

	/**
	 * @param passwordYn the passwordYn to set
	 */
	public void setPasswordYn(String passwordYn) {
		this.passwordYn = passwordYn;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		if (content == null) {
			return "";
		}
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the mailFrom
	 */
	public List<EmlMailFormFrom> getMailFrom() {
		return mailFrom;
	}

	/**
	 * @param mailFrom the mailFrom to set
	 */
	public void setMailFrom(List<EmlMailFormFrom> mailFrom) {
		this.mailFrom = mailFrom;
	}

	/**
	 * @return the fileList
	 */
/*
	public List<EmlFile> getFileList() {
		return fileList;
	}
*/

	/**
	 * @param fileList the fileList to set
	 */
/*
	public void setFileList(List<EmlFile> fileList) {
		this.fileList = fileList;
	}
*/

	/**
	 * @return the fileDownEndDe
	 */
	public Date getFileDownEndDe() {
		return fileDownEndDe;
	}

	/**
	 * @param fileDownEndDe the fileDownEndDe to set
	 */
	public void setFileDownEndDe(Date fileDownEndDe) {
		this.fileDownEndDe = fileDownEndDe;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the afterPage
	 */
	public String getAfterPage() {
		return afterPage;
	}

	/**
	 * @param afterPage the afterPage to set
	 */
	public void setAfterPage(String afterPage) {
		this.afterPage = afterPage;
	}

	/**
	 * @return the reservDt
	 */
	public Date getReservDt() {
		return reservDt;
	}

	/**
	 * @param reservDt the reservDt to set
	 */
	public void setReservDt(Date reservDt) {
		this.reservDt = reservDt;
	}

	/**
	 * @return the timeZoneId
	 */
	public String getTimeZoneId() {
		return timeZoneId;
	}

	/**
	 * @param timeZoneId the timeZoneId to set
	 */
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	/**
	 * @return the mailIdList
	 */
	public String getMailIdList() {
		return StringUtils.defaultString(mailIdList);
	}

	/**
	 * @param mailIdList the mailIdList to set
	 */
	public void setMailIdList(String mailIdList) {
		this.mailIdList = mailIdList;
	}

	/**
	 * @return the storeExpireDe
	 */
	public Date getStoreExpireDe() {
		return storeExpireDe;
	}

	/**
	 * @param storeExpireDe the storeExpireDe to set
	 */
	public void setStoreExpireDe(Date storeExpireDe) {
		this.storeExpireDe = storeExpireDe;
	}

	/**
	 * @return the mailTo
	 */
	public List<EmlMailFormTo> getMailTo() {
		return mailTo;
	}

	/**
	 * @param mailTo the mailTo to set
	 */
	public void setMailTo(List<EmlMailFormTo> mailTo) {
		this.mailTo = mailTo;
	}

	/**
	 * @return the mailCc
	 */
	public List<EmlMailFormCc> getMailCc() {
		return mailCc;
	}

	/**
	 * @param mailCc the mailCc to set
	 */
	public void setMailCc(List<EmlMailFormCc> mailCc) {
		this.mailCc = mailCc;
	}

	/**
	 * @return the mailBcc
	 */
	public List<EmlMailFormBcc> getMailBcc() {
		return mailBcc;
	}

	/**
	 * @param mailBcc the mailBcc to set
	 */
	public void setMailBcc(List<EmlMailFormBcc> mailBcc) {
		this.mailBcc = mailBcc;
	}

	/**
	 * @return the mailReadRecpinYn
	 */
	public String getMailReadRecpinYn() {
		return mailReadRecpinYn;
	}

	/**
	 * @param mailReadRecpinYn the mailReadRecpinYn to set
	 */
	public void setMailReadRecpinYn(String mailReadRecpinYn) {
		this.mailReadRecpinYn = mailReadRecpinYn;
	}

	/**
	 * @return the langCd
	 */
	public String getLangCd() {
		return langCd;
	}

	/**
	 * @param langCd the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * @return the remoteAddr
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}

	/**
	 * @param remoteAddr the remoteAddr to set
	 */
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

    @Override
    public String toString() {
        return "EmlMailForm{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", subject='" + subject + '\'' +
                ", priority='" + priority + '\'' +
                ", separateSendYn='" + separateSendYn + '\'' +
                ", massiveMailYn='" + massiveMailYn + '\'' +
                ", password='" + password + '\'' +
                ", passwordYn='" + passwordYn + '\'' +
                ", content='" + content + '\'' +
                ", mailFrom=" + mailFrom +
                ", mailTo=" + mailTo +
                ", mailCc=" + mailCc +
                ", mailBcc=" + mailBcc +
                ", fileDownEndDe='" + fileDownEndDe + '\'' +
                ", mode='" + mode + '\'' +
                ", afterPage='" + afterPage + '\'' +
                ", reservDt='" + reservDt + '\'' +
                ", timeZoneId='" + timeZoneId + '\'' +
                ", mailIdList='" + mailIdList + '\'' +
                ", storeExpireDe='" + storeExpireDe + '\'' +
                ", mailReadRecpinYn='" + mailReadRecpinYn + '\'' +
                ", langCd='" + langCd + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                '}';
    }
}
