package com.oreumio.james.rest.group;

import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlGroupConfigVo {
    private String filterSocket;
    private String ignoreUserQuota;
    private long defaultUserQuota;
    /**
     * 첨부할 수 있는 최대 크기
     * 연결 첨부이든 삽입 첨부이든.
     */
    private long defaultMaxAttachmentSize;
    /**
     * 고객 쿼타
     */
    private long serverQuota;
    /**
     * 대용량 첨부로 바뀌는 크기
     */
    private long attachmentThresholdSize;
    private String attachmentDownloadTime;
    private String maintSpam;
    private String maintSpamTime;
    private String maintDeletedMail;
    private String maintDeletedMailTime;
    private String maintXFERLog;
    private String maintXFERLogTime;
    private String maintAPPRLog;
    private String maintAPPRLogTime;
    private String maintSECLog;
    private String maintSECLogTime;
    private String notifyOverQuotaThreshold;
    private String withdrawalType;
    private String maintReceivedMail;
    private String maintReceivedMailTime;
    private String maintSentMail;
    private String maintSentMailTime;
    private String useSignature;
    private String signature;
    private String useSecurity;
    private List<EmlSecurityFilterVo> securityFilterList;

    public String getFilterSocket() {
        return filterSocket;
    }

    public void setFilterSocket(String filterSocket) {
        this.filterSocket = filterSocket;
    }

    public String getIgnoreUserQuota() {
        return ignoreUserQuota;
    }

    public void setIgnoreUserQuota(String ignoreUserQuota) {
        this.ignoreUserQuota = ignoreUserQuota;
    }

    public long getDefaultUserQuota() {
        return defaultUserQuota;
    }

    public void setDefaultUserQuota(long defaultUserQuota) {
        this.defaultUserQuota = defaultUserQuota;
    }

    public long getDefaultMaxAttachmentSize() {
        return defaultMaxAttachmentSize;
    }

    public void setDefaultMaxAttachmentSize(long defaultMaxAttachmentSize) {
        this.defaultMaxAttachmentSize = defaultMaxAttachmentSize;
    }

    public long getServerQuota() {
        return serverQuota;
    }

    public void setServerQuota(long serverQuota) {
        this.serverQuota = serverQuota;
    }

    public long getAttachmentThresholdSize() {
        return attachmentThresholdSize;
    }

    public void setAttachmentThresholdSize(long attachmentThresholdSize) {
        this.attachmentThresholdSize = attachmentThresholdSize;
    }

    public String getAttachmentDownloadTime() {
        return attachmentDownloadTime;
    }

    public void setAttachmentDownloadTime(String attachmentDownloadTime) {
        this.attachmentDownloadTime = attachmentDownloadTime;
    }

    public String getMaintSpam() {
        return maintSpam;
    }

    public void setMaintSpam(String maintSpam) {
        this.maintSpam = maintSpam;
    }

    public String getMaintSpamTime() {
        return maintSpamTime;
    }

    public void setMaintSpamTime(String maintSpamTime) {
        this.maintSpamTime = maintSpamTime;
    }

    public String getMaintDeletedMail() {
        return maintDeletedMail;
    }

    public void setMaintDeletedMail(String maintDeletedMail) {
        this.maintDeletedMail = maintDeletedMail;
    }

    public String getMaintDeletedMailTime() {
        return maintDeletedMailTime;
    }

    public void setMaintDeletedMailTime(String maintDeletedMailTime) {
        this.maintDeletedMailTime = maintDeletedMailTime;
    }

    public String getMaintXFERLog() {
        return maintXFERLog;
    }

    public void setMaintXFERLog(String maintXFERLog) {
        this.maintXFERLog = maintXFERLog;
    }

    public String getMaintXFERLogTime() {
        return maintXFERLogTime;
    }

    public void setMaintXFERLogTime(String maintXFERLogTime) {
        this.maintXFERLogTime = maintXFERLogTime;
    }

    public String getMaintAPPRLog() {
        return maintAPPRLog;
    }

    public void setMaintAPPRLog(String maintAPPRLog) {
        this.maintAPPRLog = maintAPPRLog;
    }

    public String getMaintAPPRLogTime() {
        return maintAPPRLogTime;
    }

    public void setMaintAPPRLogTime(String maintAPPRLogTime) {
        this.maintAPPRLogTime = maintAPPRLogTime;
    }

    public String getMaintSECLog() {
        return maintSECLog;
    }

    public void setMaintSECLog(String maintSECLog) {
        this.maintSECLog = maintSECLog;
    }

    public String getMaintSECLogTime() {
        return maintSECLogTime;
    }

    public void setMaintSECLogTime(String maintSECLogTime) {
        this.maintSECLogTime = maintSECLogTime;
    }

    public String getNotifyOverQuotaThreshold() {
        return notifyOverQuotaThreshold;
    }

    public void setNotifyOverQuotaThreshold(String notifyOverQuotaThreshold) {
        this.notifyOverQuotaThreshold = notifyOverQuotaThreshold;
    }

    public String getWithdrawalType() {
        return withdrawalType;
    }

    public void setWithdrawalType(String withdrawalType) {
        this.withdrawalType = withdrawalType;
    }

    public String getMaintReceivedMail() {
        return maintReceivedMail;
    }

    public void setMaintReceivedMail(String maintReceivedMail) {
        this.maintReceivedMail = maintReceivedMail;
    }

    public String getMaintReceivedMailTime() {
        return maintReceivedMailTime;
    }

    public void setMaintReceivedMailTime(String maintReceivedMailTime) {
        this.maintReceivedMailTime = maintReceivedMailTime;
    }

    public String getMaintSentMail() {
        return maintSentMail;
    }

    public void setMaintSentMail(String maintSentMail) {
        this.maintSentMail = maintSentMail;
    }

    public String getMaintSentMailTime() {
        return maintSentMailTime;
    }

    public void setMaintSentMailTime(String maintSentMailTime) {
        this.maintSentMailTime = maintSentMailTime;
    }

    public String getUseSignature() {
        return useSignature;
    }

    public void setUseSignature(String useSignature) {
        this.useSignature = useSignature;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUseSecurity() {
        return useSecurity;
    }

    public void setUseSecurity(String useSecurity) {
        this.useSecurity = useSecurity;
    }

    public List<EmlSecurityFilterVo> getSecurityFilterList() {
        return securityFilterList;
    }

    public void setSecurityFilterList(List<EmlSecurityFilterVo> securityFilterList) {
        this.securityFilterList = securityFilterList;
    }

    @Override
    public String toString() {
        return "EmlGroupConfigVo{" +
                "filterSocket='" + filterSocket + '\'' +
                ", ignoreUserQuota='" + ignoreUserQuota + '\'' +
                ", defaultUserQuota=" + defaultUserQuota +
                ", defaultMaxAttachmentSize=" + defaultMaxAttachmentSize +
                ", serverQuota=" + serverQuota +
                ", attachmentThresholdSize=" + attachmentThresholdSize +
                ", attachmentDownloadTime='" + attachmentDownloadTime + '\'' +
                ", maintSpam='" + maintSpam + '\'' +
                ", maintSpamTime='" + maintSpamTime + '\'' +
                ", maintDeletedMail='" + maintDeletedMail + '\'' +
                ", maintDeletedMailTime='" + maintDeletedMailTime + '\'' +
                ", maintXFERLog='" + maintXFERLog + '\'' +
                ", maintXFERLogTime='" + maintXFERLogTime + '\'' +
                ", maintAPPRLog='" + maintAPPRLog + '\'' +
                ", maintAPPRLogTime='" + maintAPPRLogTime + '\'' +
                ", maintSECLog='" + maintSECLog + '\'' +
                ", maintSECLogTime='" + maintSECLogTime + '\'' +
                ", notifyOverQuotaThreshold='" + notifyOverQuotaThreshold + '\'' +
                ", withdrawalType='" + withdrawalType + '\'' +
                ", maintReceivedMail='" + maintReceivedMail + '\'' +
                ", maintReceivedMailTime='" + maintReceivedMailTime + '\'' +
                ", maintSentMail='" + maintSentMail + '\'' +
                ", maintSentMailTime='" + maintSentMailTime + '\'' +
                ", useSignature='" + useSignature + '\'' +
                ", signature=" + signature +
                ", useSecurity='" + useSecurity + '\'' +
                ", securityFilterList=" + securityFilterList +
                '}';
    }
}
