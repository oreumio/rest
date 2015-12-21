package com.oreumio.james.rest.send;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * <pre>
 * 대용량 메일
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_MASSIVE_MAIL")
public class EmlMassiveMail implements Serializable {

	private static final long serialVersionUID = 8675974233704699615L;

	/**
	 * 대용량 메일 고유번호
	 */
	@Id
	@Column(name = "MASS_MAIL_ID", nullable = false, length = 30)
	private String massMailId;

	/**
	 * 메일 정보
	 */
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "emlMassiveMail")
	private Collection<EmlMassiveFile> emlMassiveFiles;

	/**
	 * 대용량 메일 상세 정보 (1 : 0 .. 1 관계)
	 * 필요하면 주석 해제할 것
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<EmlMassiveMailDetail> emlMassiveMailDetails;
	 */

	/**
	 * 파일 다운로드 만료일
	 */
	@Column(name = "FILE_DOWN_END_DT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fileDownEndDt;

	/**
	 * 등록일
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
	 * @return the emlMassiveFiles
	 */
	public Collection<EmlMassiveFile> getEmlMassiveFiles() {
		return emlMassiveFiles;
	}

	/**
	 * @param emlMassiveFiles the emlMassiveFiles to set
	 */
	public void setEmlMassiveFiles(Collection<EmlMassiveFile> emlMassiveFiles) {
		this.emlMassiveFiles = emlMassiveFiles;
	}

	/**
	 * @return the fileDownEndDt
	 */
	public Date getFileDownEndDt() {
		return fileDownEndDt;
	}

	/**
	 * @param fileDownEndDt the fileDownEndDt to set
	 */
	public void setFileDownEndDt(Date fileDownEndDt) {
		this.fileDownEndDt = fileDownEndDt;
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

	/**
	 * 대용량 파일 맵핑 정보를 추가한다.
	 * @param emlMassiveFile 대용량 파일 맵핑 domain
	 */
	public void addEmlMassiveFile(EmlMassiveFile emlMassiveFile) {
		if (this.emlMassiveFiles == null) {
			this.emlMassiveFiles = new ArrayList<EmlMassiveFile>();
		}
		this.emlMassiveFiles.add(emlMassiveFile);
		emlMassiveFile.setEmlMassiveMail(this);
	}
}
