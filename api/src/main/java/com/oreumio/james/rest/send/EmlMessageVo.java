package com.oreumio.james.rest.send;

import com.oreumio.james.rest.file.EmlFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * mime 정보가 저장될 vo
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlMessageVo implements Serializable {

	private static final long serialVersionUID = 5539807309902291512L;

	/**
	 * 제목
	 */
	private String subject;

	/**
	 * 본문 내용
	 */
	private String content;

	/**
	 * 보낸 사람
	 */
	private String from;

	/**
	 * 조직도 정보 포함된 보낸 사람
	 */
	private String orgFrom;

	/**
	 * 받는 사람
	 */
	private List<String> to;

	/**
	 * 참조
	 */
	private List<String> cc;

	/**
	 * 숨은 참조
	 */
	private List<String> bcc;

	/**
	 * 보낸 일시
	 */
	private Date sendDt;

	/**
	 * 받은 일시
	 */
	private Date recvDt;

	/** 메일 첨부 파일. */
	private List<EmlFile> attachFileList;

	/**
	 * 
	 */
	private String charset;

	/**
	 * 중요메일 여부
	 */
	private boolean prioritized;

	/**
	 * 보안메일 여부
	 */
	private boolean secured;

	/**
	 * 예약 메일 타임존
	 */
	private String timeZoneId;

	/**
	 * 예약 시간
	 */
	private String reservDt;

	/**
	 * 예약 날짜
	 */
	private String reservTime;

	/**
	 * 비밀번호
	 */
	private String password;

	/**
	 * 읽음 여부 메일을 발송받을 이메일 주소
	 */
	private String dispNotiEmail;

	/**
	 * 친구 맺기 수신 여부(조회시 버튼 표시 유무)
	 */
	private String displayBuddyBtn;

	/**
	 * 친구 맺기 수신 여부(임시 저장 수정시 기존 체크 여부 유지)
	 */
	private String buddyJoinRecpinYn;

	/**
	 * 삭제여부
	 */
	private String delYn;

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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the orgFrom
	 */
	public String getOrgFrom() {
		return orgFrom;
	}

	/**
	 * @param orgFrom the orgFrom to set
	 */
	public void setOrgFrom(String orgFrom) {
		this.orgFrom = orgFrom;
	}

	/**
	 * @return the to
	 */
	public List<String> getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(List<String> to) {
		this.to = to;
	}

	/**
	 * @return the cc
	 */
	public List<String> getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	/**
	 * @return the bcc
	 */
	public List<String> getBcc() {
		return bcc;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the sendDt
	 */
	public Date getSendDt() {
		return sendDt;
	}

	/**
	 * @param sendDt the sendDt to set
	 */
	public void setSendDt(Date sendDt) {
		this.sendDt = sendDt;
	}

	/**
	 * @return the recvDt
	 */
	public Date getRecvDt() {
		return recvDt;
	}

	/**
	 * @param recvDt the recvDt to set
	 */
	public void setRecvDt(Date recvDt) {
		this.recvDt = recvDt;
	}

	/**
	 * @return the attachFileList
	 */
	public List<EmlFile> getAttachFileList() {
		return attachFileList;
	}

	/**
	 * @param attachFileList the attachFileList to set
	 */
	public void setAttachFileList(List<EmlFile> attachFileList) {
		this.attachFileList = attachFileList;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the prioritized
	 */
	public boolean isPrioritized() {
		return prioritized;
	}

	/**
	 * @param prioritized the prioritized to set
	 */
	public void setPrioritized(boolean prioritized) {
		this.prioritized = prioritized;
	}

	/**
	 * @return the secured
	 */
	public boolean isSecured() {
		return secured;
	}

	/**
	 * @param secured the secured to set
	 */
	public void setSecured(boolean secured) {
		this.secured = secured;
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
	 * @return the dispNotiEmail
	 */
	public String getDispNotiEmail() {
		return dispNotiEmail;
	}

	/**
	 * @param dispNotiEmail the dispNotiEmail to set
	 */
	public void setDispNotiEmail(String dispNotiEmail) {
		this.dispNotiEmail = dispNotiEmail;
	}

	/**
	 * @return the displayBuddyBtn
	 */
	public String getDisplayBuddyBtn() {
		return displayBuddyBtn;
	}

	/**
	 * @param displayBuddyBtn the displayBuddyBtn to set
	 */
	public void setDisplayBuddyBtn(String displayBuddyBtn) {
		this.displayBuddyBtn = displayBuddyBtn;
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

	/**
	 * @return the delYn
	 */
	public String getDelYn() {
		return delYn;
	}

	/**
	 * @param delYn the delYn to set
	 */
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
}
