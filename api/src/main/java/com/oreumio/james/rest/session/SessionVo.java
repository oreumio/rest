package com.oreumio.james.rest.session;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class SessionVo {

    private String userId = "U1";
    private String groupId = "G1";
    private String clientId = "C1";

    public SessionVo() {
    }

    public SessionVo(String userId, String groupId, String clientId) {
        this.userId = userId;
        this.groupId = groupId;
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
