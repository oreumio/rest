package com.oreumio.james.rest.send;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <pre>
 * 대용량 파일
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_MASSIVE_FILE")
public class EmlMassiveFile implements Serializable {

	private static final long serialVersionUID = -6082250530209371106L;

	/**
	 * 파일 아이디
	 */
	@Id
	@Column(name = "FILE_ID", nullable = false, length = 30)
	private String fileId;

	/**
	 * 대용량 메일 다운로드 기간
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "MASS_MAIL_ID", nullable = false)
	private EmlMassiveMail emlMassiveMail;

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
	 * @return the emlMassiveMail
	 */
	public EmlMassiveMail getEmlMassiveMail() {
		return emlMassiveMail;
	}

	/**
	 * @param emlMassiveMail the emlMassiveMail to set
	 */
	public void setEmlMassiveMail(EmlMassiveMail emlMassiveMail) {
		this.emlMassiveMail = emlMassiveMail;
	}
}
