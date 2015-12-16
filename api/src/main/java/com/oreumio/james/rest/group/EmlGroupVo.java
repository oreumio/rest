package com.oreumio.james.rest.group;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlGroupVo {
    private String id;
    private String clientId;
    private String name;
    private String state;
    private long quota;

    public EmlGroupVo() {
    }

    public EmlGroupVo(EmlGroup emlGroup) {
        id = emlGroup.getId();
        clientId = emlGroup.getClientId();
        name = emlGroup.getName();
        state = emlGroup.getState();
        quota = emlGroup.getQuota();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
