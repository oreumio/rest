package com.oreumio.james.rest.file;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 */
@Entity
@Table(name = "EML_FILE")
public class EmlFile implements Serializable {

	@Id
	@Column(name = "FILE_ID", nullable = false)
	private String fileId;

	@Column(name = "GROUP_ID", nullable = false)
	private String groupId;

	@Column(name = "FILE_GRP_ID", nullable = false)
	private String fileGrpId;

	@Column(name = "FILE_NAME", nullable = false)
	private String fileName;

	@Column(name = "FILE_TYPE", nullable = false)
	private String fileType;

	@Column(name = "FILE_PATH", nullable = false)
	private String filePath;

	@Column(name = "SAVE_FILE_NAME", nullable = false)
	private String saveFileName;

	@Column(name = "FILE_SIZE", nullable = false)
	private long fileSize;

	@Column(name = "SVC_MODULE", nullable = false)
	private String module;

	@Column(name = "REG_DT", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date regDt;

	@Transient
	private String cud;

    public EmlFile() {
    }

    public EmlFile(EmlFileVo emlFileVo) {
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
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the fileGrpId
	 */
	public String getFileGrpId() {
		return fileGrpId;
	}

	/**
	 * @param fileGrpId the fileGrpId to set
	 */
	public void setFileGrpId(String fileGrpId) {
		this.fileGrpId = fileGrpId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the saveFileName
	 */
	public String getSaveFileName() {
		return saveFileName;
	}

	/**
	 * @param saveFileName the saveFileName to set
	 */
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
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
	 * @return the cud
	 */
	public String getCud() {
		return cud;
	}

	/**
	 * @param cud the cud to set
	 */
	public void setCud(String cud) {
		this.cud = cud;
	}

	@PrePersist
	public void prePersist() {
		if (regDt == null) {
			regDt = new Date();
		}
	}
}
