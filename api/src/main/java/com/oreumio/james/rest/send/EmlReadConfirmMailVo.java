package com.oreumio.james.rest.send;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 수신 확인 메일 정보
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_READ_CONFIRM_MAIL")
public class EmlReadConfirmMailVo implements Serializable {

	private static final long serialVersionUID = 1064388236466088574L;

	/**
	 * 수신 확인 메일 고유번호
	 */
	@Id
	@Column(name = "CONFIRM_MAIL_ID", nullable = false, length = 30)
	private String confirmMailId;

	/**
	 * 이메일 아이디
	 */
	@Column(name = "EMAIL_ID", nullable = false, length = 30)
	private String emailId;

	/**
	 * 제목
	 */
	@Column(name = "SUBJECT", nullable = false, length = 300)
	private String subject;

	/**
	 * 표시용 메일 받는 사람
	 */
	@Column(name = "DISP_MAIL_TO", nullable = false, length = 255)
	private String dispMailTo;

	/**
	 * 읽은 수
	 */
	@Column(name = "READ_CNT", nullable = false)
	private int readCnt;

	/**
	 * 받는 사람 수 - 1
	 */
	@Column(name = "MAIL_TO_CNT", nullable = false)
	private int mailToCnt;

	/**
	 * 수신자 수(대표메일 주소 풀어진 갯수 포함)
	 */
	@Column(name = "RCVER_CNT", nullable = false)
	private int rcverCnt;

	/**
	 * 메일 보낸 일시
	 */
	@Column(name = "MAIL_SEND_DT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date mailSendDt;

	/**
	 * 읽음 메일 수신 여부
	 */
	@Column(name = "MAIL_READ_RECPTN_YN", nullable = false, length = 1)
	private String mailReadRecptnYn;

	/**
	 * 메일 메세지 아이디
	 */
	@Column(name = "MSG_ID", nullable = false, length = 255)
	private String msgId;

	/**
	 * 등록일시
	 */
	@Column(name = "REG_DT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDt;

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
	 * @return the dispMailTo
	 */
	public String getDispMailTo() {
		return dispMailTo;
	}

	/**
	 * @param dispMailTo the dispMailTo to set
	 */
	public void setDispMailTo(String dispMailTo) {
		this.dispMailTo = dispMailTo;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	 * @return the mailToCnt
	 */
	public int getMailToCnt() {
		return mailToCnt;
	}

	/**
	 * @param mailToCnt the mailToCnt to set
	 */
	public void setMailToCnt(int mailToCnt) {
		this.mailToCnt = mailToCnt;
	}

	/**
	 * @return the rcverCnt
	 */
	public int getRcverCnt() {
		return rcverCnt;
	}

	/**
	 * @param rcverCnt the rcverCnt to set
	 */
	public void setRcverCnt(int rcverCnt) {
		this.rcverCnt = rcverCnt;
	}

	/**
	 * @return the mailSendDt
	 */
	public Date getMailSendDt() {
		return mailSendDt;
	}

	/**
	 * @param mailSendDt the mailSendDt to set
	 */
	public void setMailSendDt(Date mailSendDt) {
		this.mailSendDt = mailSendDt;
	}

	/**
	 * @return the mailReadRecptnYn
	 */
	public String getMailReadRecptnYn() {
		return mailReadRecptnYn;
	}

	/**
	 * @param mailReadRecptnYn the mailReadRecptnYn to set
	 */
	public void setMailReadRecptnYn(String mailReadRecptnYn) {
		this.mailReadRecptnYn = mailReadRecptnYn;
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
	 * @return the regDt
	 */
	public Date getRegDt() {
		return regDt;
	}

	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
}
