package com.oreumio.james.rest.user;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlUserRoleVo {

    private String id;
    private String userId;
    private String roleId;
    private String displayName;

    public EmlUserRoleVo() {
    }

    public EmlUserRoleVo(EmlUserRole userRole) {
        id = userRole.getId();
        userId = userRole.getUserId();
        roleId = userRole.getRoleId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "EmlUserRoleVo{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
