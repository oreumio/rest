package com.oreumio.james.rest.session;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class SessionVo {

    private String userId;
    private String groupId;

    public SessionVo() {
    }

    public SessionVo(String userId, String groupId) {
        this.userId = userId;
        this.groupId = groupId;
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
}
