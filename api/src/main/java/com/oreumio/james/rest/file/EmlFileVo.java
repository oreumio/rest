package com.oreumio.james.rest.file;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * </pre>
 * 
 */
public class EmlFileVo implements Serializable {

	private String fileId;

	private String groupId;

	private String fileGrpId;

	private String fileName;

	private String fileType;

	private String filePath;

	private String saveFileName;

	private long fileSize;

	private String module;

	private Date regDt;

	private String cud;

    public EmlFileVo() {
    }

    public EmlFileVo(EmlFile cmmFile) {
        fileId = cmmFile.getFileId();
        groupId = cmmFile.getGroupId();
        fileGrpId = cmmFile.getFileGrpId();
        fileName = cmmFile.getFileName();
        fileType = cmmFile.getFileType();
        filePath = cmmFile.getFilePath();
        saveFileName = cmmFile.getSaveFileName();
        fileSize = cmmFile.getFileSize();
        module = cmmFile.getModule();
        regDt = cmmFile.getRegDt();
        cud = cmmFile.getCud();
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
