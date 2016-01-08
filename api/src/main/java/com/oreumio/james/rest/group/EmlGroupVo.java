package com.oreumio.james.rest.group;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlGroupVo {
    private String id;
    private String clientId;
    private String host;
    private String userName;
    private String password;
    private String alg;
    private String state;
    private long quota;
    private String displayName;
    private EmlGroupConfigVo serverConfig;

    public EmlGroupVo() {
    }

    public EmlGroupVo(EmlGroup group) {
        id = group.getId();
        clientId = group.getClientId();
        host = group.getHost();
        userName = group.getUserName();
        password = group.getPassword();
        alg = group.getAlg();
        state = group.getState();
        quota = group.getQuota();
        displayName = group.getDisplayName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public EmlGroupConfigVo getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(EmlGroupConfigVo serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public String toString() {
        return "EmlGroupVo{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", host='" + host + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", alg='" + alg + '\'' +
                ", state='" + state + '\'' +
                ", quota=" + quota +
                ", displayName='" + displayName + '\'' +
                ", serverConfig=" + serverConfig +
                '}';
    }
}
