package com.oreumio.james.rest.send;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 메일 발송 데이터가 저장될 vo
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlMailFormVo implements Serializable {

	private static final long serialVersionUID = 4397779894496831642L;

	/**
	 * 발송자 이메일 아이디
	 */
	private String userId;

	/**
	 * 제목
	 */
	private String subject;

	/**
	 * 중요도
	 */
	private String priority;

	/**
	 *  개별발송 여부. 
	 */
	private String separateSendYn;

	/** 대용량 메일 여부. */
	private String massiveMailYn;

	/** 비밀번호. */
	private String password;

	/** 보안메일 사용여부. */
	private String passwordYn;

	/** 메일 본문 내용. */
	private String content;

	/** 보내는 사람(이메일 주소). */
	private EmlAddressVo mailFrom;
	/**
	 * 직위 및 부서 정보
	 */
	private String organizationInfo;

	/** 메일 to. */
	private List<EmlAddressVo> mailTos;

	/** 메일 cc. */
	private List<EmlAddressVo> mailCcs;

	/** 메일 bcc. */
	private List<EmlAddressVo> mailBccs;

	/** 첨부파일 목록. */
	private List<String> fileList;

	/** 대용량 메일 첨부파일 다운로드 기한. */
	private String fileDownEndDe;

	/**
	 * 메일 쓰기 모드
	 * 쓰기:write,답변:reply,모두답변:allReply,전달:forward
	 * 재발송:resend,임시저장:temp,예약메일:reserv
	 */
	private String mode;

	/** 메일 전송후 이동할 페이지 코드값. */
	private String afterPage;

	/** 예약날짜(2013-12-04). */
	private String reservDt;

	/** 예약 시간(17:33). */
	private String reservTime;

    /** 예약메일 기준시간 정보(GMT+09:00). */
    private String timeZoneId;

    /**
     * 예약메일, 임시보관함 메일의 수정전 메일이나 전달시 기존 메일 정보
     */
    private List<EmlMailKeyVo> mailIdList;

    /**
     * 그룹 아이디
     */
    private String groupId;

    /** 
     * 보안 메일 조회 만료 기한.
     */
	private String storeExpireDe;

	/**
	 * 수신확인 응답 메일 받기 여부
	 */
	private String mailReadRecpinYn;

	/**
	 * 발송자 이메일
	 */
	private String email;

	/**
	 * 다국어 코드
	 */
	private String langCd;

	/**
	 * 그룹웨어 URL
	 */
	private String gwUrl;

	/**
	 * 발송자 아이피
	 */
	private String remoteAddr;

	/**
	 * 친구 맺기 수신 여부
	 */
	private String buddyJoinRecpinYn;

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
	public EmlAddressVo getMailFrom() {
		return mailFrom;
	}

	/**
	 * @param mailFrom the mailFrom to set
	 */
	public void setMailFrom(EmlAddressVo mailFrom) {
		this.mailFrom = mailFrom;
	}

	/**
	 * @return the organizationInfo
	 */
	public String getOrganizationInfo() {
		return organizationInfo;
	}

	/**
	 * @param organizationInfo the organizationInfo to set
	 */
	public void setOrganizationInfo(String organizationInfo) {
		this.organizationInfo = organizationInfo;
	}

	/**
	 * @return the fileList
	 */
	public List<String> getFileList() {
		return fileList;
	}

	/**
	 * @param fileList the fileList to set
	 */
	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	/**
	 * @return the fileDownEndDe
	 */
	public String getFileDownEndDe() {
		return fileDownEndDe;
	}

	/**
	 * @param fileDownEndDe the fileDownEndDe to set
	 */
	public void setFileDownEndDe(String fileDownEndDe) {
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
	public String getReservDt() {
		return reservDt;
	}

	/**
	 * @param reservDt the reservDt to set
	 */
	public void setReservDt(String reservDt) {
		this.reservDt = reservDt;
	}

	/**
	 * @return the reservTime
	 */
	public String getReservTime() {
		return reservTime;
	}

	/**
	 * @param reservTime the reservTime to set
	 */
	public void setReservTime(String reservTime) {
		this.reservTime = reservTime;
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
	public List<EmlMailKeyVo> getMailIdList() {
		return mailIdList;
	}

	/**
	 * @param mailIdList the mailIdList to set
	 */
	public void setMailIdList(List<EmlMailKeyVo> mailIdList) {
		this.mailIdList = mailIdList;
	}

	/**
	 * @return the groupId
	 */
	public String getCmpId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the storeExpireDe
	 */
	public String getStoreExpireDe() {
		return storeExpireDe;
	}

	/**
	 * @param storeExpireDe the storeExpireDe to set
	 */
	public void setStoreExpireDe(String storeExpireDe) {
		this.storeExpireDe = storeExpireDe;
	}

	/**
	 * @return the mailTos
	 */
	public List<EmlAddressVo> getMailTos() {
		return mailTos;
	}

	/**
	 * @param mailTos the mailTos to set
	 */
	public void setMailTos(List<EmlAddressVo> mailTos) {
		this.mailTos = mailTos;
	}

	/**
	 * @return the mailCcs
	 */
	public List<EmlAddressVo> getMailCcs() {
		return mailCcs;
	}

	/**
	 * @param mailCcs the mailCcs to set
	 */
	public void setMailCcs(List<EmlAddressVo> mailCcs) {
		this.mailCcs = mailCcs;
	}

	/**
	 * @return the mailBccs
	 */
	public List<EmlAddressVo> getMailBccs() {
		return mailBccs;
	}

	/**
	 * @param mailBccs the mailBccs to set
	 */
	public void setMailBccs(List<EmlAddressVo> mailBccs) {
		this.mailBccs = mailBccs;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the gwUrl
	 */
	public String getGwUrl() {
		return gwUrl;
	}

	/**
	 * @param gwUrl the gwUrl to set
	 */
	public void setGwUrl(String gwUrl) {
		this.gwUrl = gwUrl;
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

	/**
	 * @return the buddyJoinRecpinYn
	 */
	public String getBuddyJoinRecpinYn() {
		return buddyJoinRecpinYn;
	}

	/**
	 * @param buddyJoinRecpinYn the buddyJoinRecpinYn to set
	 */
	public void setBuddyJoinRecpinYn(String buddyJoinRecpinYn) {
		this.buddyJoinRecpinYn = buddyJoinRecpinYn;
	}

    @Override
    public String toString() {
        return "EmlMailFormVo{" +
                "userId='" + userId + '\'' +
                ", subject='" + subject + '\'' +
                ", priority='" + priority + '\'' +
                ", separateSendYn='" + separateSendYn + '\'' +
                ", massiveMailYn='" + massiveMailYn + '\'' +
                ", password='" + password + '\'' +
                ", passwordYn='" + passwordYn + '\'' +
                ", content='" + content + '\'' +
                ", mailFrom='" + mailFrom + '\'' +
                ", organizationInfo='" + organizationInfo + '\'' +
                ", mailTos='" + mailTos + '\'' +
                ", mailCcs='" + mailCcs + '\'' +
                ", mailBccs='" + mailBccs + '\'' +
                ", fileList=" + fileList +
                ", fileDownEndDe='" + fileDownEndDe + '\'' +
                ", mode='" + mode + '\'' +
                ", afterPage='" + afterPage + '\'' +
                ", reservDt='" + reservDt + '\'' +
                ", reservTime='" + reservTime + '\'' +
                ", timeZoneId='" + timeZoneId + '\'' +
                ", mailIdList='" + mailIdList + '\'' +
                ", groupId='" + groupId + '\'' +
                ", storeExpireDe='" + storeExpireDe + '\'' +
                ", mailReadRecpinYn='" + mailReadRecpinYn + '\'' +
                ", email='" + email + '\'' +
                ", langCd='" + langCd + '\'' +
                ", gwUrl='" + gwUrl + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", buddyJoinRecpinYn='" + buddyJoinRecpinYn + '\'' +
                '}';
    }
}
