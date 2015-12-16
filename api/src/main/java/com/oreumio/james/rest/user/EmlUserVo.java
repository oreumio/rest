package com.oreumio.james.rest.user;

import java.util.Date;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlUserVo {

    private String id;
    private String groupId;
    private String userName;
    private String socketEnabled;
    private String apprMailExceptYn;
    private String mailAutodelExceptYn;
    private String state;
    private long quota;
    private long attachmentMaxSize;
    private Date updDt;
    private String config;
    private Date orgRegDt;

    public EmlUserVo() {
    }

    public EmlUserVo(EmlUser emlUser) {
        id = emlUser.getId();
        groupId = emlUser.getGroupId();
        userName = emlUser.getUserName();
        socketEnabled = emlUser.getSocketEnabled();
        apprMailExceptYn = emlUser.getApprMailExceptYn();
        mailAutodelExceptYn = emlUser.getMailAutodelExceptYn();
        state = emlUser.getState();
        quota = emlUser.getQuota();
        attachmentMaxSize = emlUser.getAttachmentMaxSize();
        updDt = emlUser.getUpdDt();
        config = emlUser.getConfig();
        orgRegDt = emlUser.getOrgRegDt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSocketEnabled() {
        return socketEnabled;
    }

    public void setSocketEnabled(String socketEnabled) {
        this.socketEnabled = socketEnabled;
    }

    public String getApprMailExceptYn() {
        return apprMailExceptYn;
    }

    public void setApprMailExceptYn(String apprMailExceptYn) {
        this.apprMailExceptYn = apprMailExceptYn;
    }

    public String getMailAutodelExceptYn() {
        return mailAutodelExceptYn;
    }

    public void setMailAutodelExceptYn(String mailAutodelExceptYn) {
        this.mailAutodelExceptYn = mailAutodelExceptYn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getQuota() {
        return quota;
    }

    public void setQuota(long quota) {
        this.quota = quota;
    }

    public long getAttachmentMaxSize() {
        return attachmentMaxSize;
    }

    public void setAttachmentMaxSize(long attachmentMaxSize) {
        this.attachmentMaxSize = attachmentMaxSize;
    }

    public Date getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Date getOrgRegDt() {
        return orgRegDt;
    }

    public void setOrgRegDt(Date orgRegDt) {
        this.orgRegDt = orgRegDt;
    }

    @Override
    public String toString() {
        return "EmlUserVo{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", userName='" + userName + '\'' +
                ", socketEnabled='" + socketEnabled + '\'' +
                ", apprMailExceptYn='" + apprMailExceptYn + '\'' +
                ", mailAutodelExceptYn='" + mailAutodelExceptYn + '\'' +
                ", state='" + state + '\'' +
                ", quota=" + quota +
                ", attachmentMaxSize=" + attachmentMaxSize +
                ", updDt=" + updDt +
                ", config='" + config + '\'' +
                ", orgRegDt=" + orgRegDt +
                '}';
    }
}
