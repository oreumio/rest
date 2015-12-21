package com.oreumio.james.rest.send;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 대용량 메일 상세
 * </pre>
 * 
 * @author  : doyoung Choi
 * @version : 1.0.0
 */
@Entity
@Table(name = "EML_MASSIVE_MAIL_DETAIL")
public class EmlMassiveMailDetail implements Serializable {

	private static final long serialVersionUID = 3004974282985996887L;

	/**
	 * 대용량 메일 고유번호
	 */
	@Id
	@Column(name = "MASS_MAIL_ID", nullable = false, length = 30)
	private String massMailId;

	/*
	 * 사용할 일이 있으면 주석 해제할 것
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "MASS_MAIL_ID", insertable = false, updatable = false)
	private EmlMassiveMail emlMassiveMail;
	*/

	/**
	 * 파일 고유번호
	 */
	@Column(name = "FILE_ID", nullable = false, length = 30)
	private String fileId;

	/**
	 * 제목
	 */
	@Column(name = "SUBJECT", nullable = false, length = 300)
	private String subject;

	/**
	 * 표시용 보낸 사람
	 */
	@Column(name = "DISP_MAIL_FROM", nullable = false, length = 255)
	private String dispMailFrom;

	/**
	 * 메일 사이즈
	 */
	@Column(name = "MAIL_SIZE", nullable = false)
	private long mailSize;

	/**
	 * 중요 메일 여부
	 */
	@Column(name = "IMPORT_YN", nullable = false, length = 1)
	private String importYn;

	/**
	 * 보안 메일 여부
	 */
	@Column(name = "SEC_MAIL_YN", nullable = false, length = 1)
	private String secMailYn;

	/**
	 * 보낸 일시
	 */
	@Column(name = "MAIL_SEND_DT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date mailSendDt;

	/**
	 * 삭제 여부
	 */
	@Column(name = "DEL_YN", nullable = false, length = 1)
	private String delYn;

	/**
	 * 회사 고유번호
	 */
	@Column(name = "CLIENT_ID", nullable = false, length = 30)
	private String clientId;
	
	/**
	 * 등록 일시
	 */
	@Column(name = "REG_DT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDt;

	/**
	 * @return the massMailId
	 */
	public String getMassMailId() {
		return massMailId;
	}

	/**
	 * @param massMailId the massMailId to set
	 */
	public void setMassMailId(String massMailId) {
		this.massMailId = massMailId;
	}

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
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
	 * @return the dispMailFrom
	 */
	public String getDispMailFrom() {
		return dispMailFrom;
	}

	/**
	 * @param dispMailFrom the dispMailFrom to set
	 */
	public void setDispMailFrom(String dispMailFrom) {
		this.dispMailFrom = dispMailFrom;
	}

	/**
	 * @return the mailSize
	 */
	public long getMailSize() {
		return mailSize;
	}

	/**
	 * @param mailSize the mailSize to set
	 */
	public void setMailSize(long mailSize) {
		this.mailSize = mailSize;
	}

	/**
	 * @return the importYn
	 */
	public String getImportYn() {
		return importYn;
	}

	/**
	 * @param importYn the importYn to set
	 */
	public void setImportYn(String importYn) {
		this.importYn = importYn;
	}

	/**
	 * @return the secMailYn
	 */
	public String getSecMailYn() {
		return secMailYn;
	}

	/**
	 * @param secMailYn the secMailYn to set
	 */
	public void setSecMailYn(String secMailYn) {
		this.secMailYn = secMailYn;
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

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	/**
	 * 날짜 초기값
	 */
	@PrePersist
	public void prePersist() {
	    this.regDt = new Date();
	}
}
