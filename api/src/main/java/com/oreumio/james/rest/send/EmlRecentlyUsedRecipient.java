package com.oreumio.james.rest.send;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 최근 사용한 메일 주소
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_RECENTLY_USED_RECIPIENT")
public class EmlRecentlyUsedRecipient implements Serializable {

	private static final long serialVersionUID = 3215215615047211363L;

	/**
	 * 고유 번호
	 */
	@Id
	@Column(name = "ID", nullable = false, length = 30)
	private String id;

	/**
	 * 이메일 아이디
	 */
	@Column(name = "USER_ID", nullable = false, length = 30)
	private String userId;

	/**
	 * 받는 사람 이메일 주소
	 */
	@Column(name = "MAIL_TO", nullable = false, length = 255)
	private String mailTo;

	/**
	 * 등록일
	 */
	@Column(name = "REG_DT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDt;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
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
	 * DB 저장전에 날짜 셋팅
	 */
	@PrePersist
	public void prePersist() {
	    this.regDt = new Date();
	}
}
