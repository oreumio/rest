package com.oreumio.james.rest.system;

import com.oreumio.james.rest.group.EmlClient;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlSystemVo {
    private String id;
    private String userName;
    private String host;
    private String password;
    private String alg;
    private String displayName;
    private String state;
    private long quota;

    public EmlSystemVo() {
    }

    public EmlSystemVo(EmlClient client) {
        id = client.getId();
        userName = client.getUserName();
        host = client.getHost();
        password = client.getPassword();
        alg = client.getAlg();
        displayName = client.getDisplayName();
        state = client.getState();
        quota = client.getQuota();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    @Override
    public String toString() {
        return "EmlClientVo{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", host='" + host + '\'' +
                ", password='" + password + '\'' +
                ", alg='" + alg + '\'' +
                ", displayName='" + displayName + '\'' +
                ", state='" + state + '\'' +
                ", quota=" + quota +
                '}';
    }
}
