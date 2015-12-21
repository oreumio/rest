package com.oreumio.james.rest.send;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 수신 확인 세부 정보
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_READ_CONFIRM")
public class EmlReadConfirmVo implements Serializable {

	private static final long serialVersionUID = -2175244431710154924L;

	/**
	 * 수신 확인 고유번호
	 */
	@Id
	@Column(name = "CONFIRM_ID", nullable = false, length = 30)
	private String confirmId;

	/**
	 * 수신 확인 부모 아이디
	 */
	@Column(name = "CONFIRM_MAIL_ID", nullable = false, length = 30)
	private String confirmMailId;

	/**
	 * 메세지 아이디
	 */
	@Column(name = "MSG_ID", nullable = false, length = 255)
	private String msgId;

	/**
	 * 메일 받는 사람
	 */
	@Column(name = "MAIL_TO", nullable = false, length = 255)
	private String mailTo;

	/**
	 * 메일 받는 사람 유형(I:내부,E:외부)
	 */
	@Column(name = "MAIL_TO_TYPE", nullable = false, length = 1)
	private String mailToType;
	
	
	/**
	 * 메일 받는 사람 이메일 아이디
	 */
	@Column(name = "MAIL_TO_EMAIL_ID", nullable = true, length = 30)
	private String mailToEmailId;
	
	/**
	 * 처음 읽은 일시
	 */
	@Column(name = "FRST_READ_DT", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date frstReadDt;

	/**
	 * 마지막 읽은 일시
	 */
	@Column(name = "LAST_READ_DT", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastReadDt;

	/**
	 * 읽은 횟수
	 */
	@Column(name = "READ_CNT", nullable = false)
	private int readCnt;

	/**
	 * <pre>
	 * 처리 상태
	 * N : 정상 (Normal)
	 * R : 회수 (Recovery)
	 * B : 발신실패
	 * </pre>
	 */
	@Column(name = "PROC_STATUS", nullable = false, length = 1)
	private String procStatus;

	/**
	 * @return the confirmId
	 */
	public String getConfirmId() {
		return confirmId;
	}

	/**
	 * @param confirmId the confirmId to set
	 */
	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}

	/**
	 * @return the msgId
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the mailTo
	 */
	public String getMailTo() {
		return mailTo;
	}

	/**
	 * @param mailTo the mailTo to set
	 */
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	/**
	 * @return the mailToType
	 */
	public String getMailToType() {
		return mailToType;
	}

	/**
	 * @param mailToType the mailToType to set
	 */
	public void setMailToType(String mailToType) {
		this.mailToType = mailToType;
	}

	/**
	 * @return the mailToEmailId
	 */
	public String getMailToEmailId() {
		return mailToEmailId;
	}

	/**
	 * @param mailToEmailId the mailToEmailId to set
	 */
	public void setMailToEmailId(String mailToEmailId) {
		this.mailToEmailId = mailToEmailId;
	}

	/**
	 * @return the frstReadDt
	 */
	public Date getFrstReadDt() {
		return frstReadDt;
	}

	/**
	 * @param frstReadDt the frstReadDt to set
	 */
	public void setFrstReadDt(Date frstReadDt) {
		this.frstReadDt = frstReadDt;
	}

	/**
	 * @return the lastReadDt
	 */
	public Date getLastReadDt() {
		return lastReadDt;
	}

	/**
	 * @param lastReadDt the lastReadDt to set
	 */
	public void setLastReadDt(Date lastReadDt) {
		this.lastReadDt = lastReadDt;
	}

	/**
	 * @return the readCnt
	 */
	public int getReadCnt() {
		return readCnt;
	}

	/**
	 * @param readCnt the readCnt to set
	 */
	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}

	/**
	 * @return the procStatus
	 */
	public String getProcStatus() {
		return procStatus;
	}

	/**
	 * @param procStatus the procStatus to set
	 */
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	/**
	 * @return the confirmMailId
	 */
	public String getConfirmMailId() {
		return confirmMailId;
	}

	/**
	 * @param confirmMailId the confirmMailId to set
	 */
	public void setConfirmMailId(String confirmMailId) {
		this.confirmMailId = confirmMailId;
	}
}
