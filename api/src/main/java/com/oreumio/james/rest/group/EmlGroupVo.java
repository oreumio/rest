package com.oreumio.james.rest.group;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlGroupVo {
    private String id;
    private String clientId;
    private String state;
    private long quota;
    private String displayName;
    private EmlGroupConfigVo serverConfig;

    public EmlGroupVo() {
    }

    public EmlGroupVo(EmlGroup emlGroup) {
        id = emlGroup.getId();
        clientId = emlGroup.getClientId();
        state = emlGroup.getState();
        quota = emlGroup.getQuota();
        displayName = emlGroup.getDisplayName();
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
                ", state='" + state + '\'' +
                ", quota=" + quota +
                ", displayName='" + displayName + '\'' +
                ", serverConfig=" + serverConfig +
                '}';
    }
}
