package com.oreumio.james.rest.user;

import java.util.Date;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlUserVo {

    private String id;
    private String groupId;
    private String userName;
    private String domainName;
    private String password;
    private String displayName;
    private String socketEnabled;
    private String apprMailExceptYn;
    private String mailAutodelExceptYn;
    private String state;
    private long quota;
    private long attachmentMaxSize;
    private Date updDt;
    private String serverConfig;
    private String clientConfig;
    private Date orgRegDt;

    public EmlUserVo() {
    }

    public EmlUserVo(EmlUser emlUser) {
        id = emlUser.getId();
        groupId = emlUser.getGroupId();
        userName = emlUser.getUserName();
        domainName = emlUser.getDomainName();
        password = emlUser.getPassword();
        displayName = emlUser.getDisplayName();
        socketEnabled = emlUser.getSocketEnabled();
        apprMailExceptYn = emlUser.getApprMailExceptYn();
        mailAutodelExceptYn = emlUser.getMailAutodelExceptYn();
        state = emlUser.getState();
        quota = emlUser.getQuota();
        attachmentMaxSize = emlUser.getAttachmentMaxSize();
        updDt = emlUser.getUpdDt();
        serverConfig = emlUser.getServerConfig();
        clientConfig = emlUser.getClientConfig();
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

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(String serverConfig) {
        this.serverConfig = serverConfig;
    }

    public String getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(String clientConfig) {
        this.clientConfig = clientConfig;
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
                ", domainName='" + domainName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", socketEnabled='" + socketEnabled + '\'' +
                ", apprMailExceptYn='" + apprMailExceptYn + '\'' +
                ", mailAutodelExceptYn='" + mailAutodelExceptYn + '\'' +
                ", state='" + state + '\'' +
                ", quota=" + quota +
                ", attachmentMaxSize=" + attachmentMaxSize +
                ", updDt=" + updDt +
                ", serverConfig='" + serverConfig + '\'' +
                ", clientConfig='" + clientConfig + '\'' +
                ", orgRegDt=" + orgRegDt +
                '}';
    }
}
