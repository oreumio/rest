package com.oreumio.james.rest.org;

/**
 * 사용자 조직
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlUserOrgVo {

    private String id;

    private String orgId;

    private String orgUnitId;

    private String userId;

    public EmlUserOrgVo() {
    }

    public EmlUserOrgVo(EmlUserOrg emlUserOrg) {
        id = emlUserOrg.getId();
        orgUnitId = emlUserOrg.getOrgSystemUnitId();
        userId = emlUserOrg.getUserId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "EmlUserOrg{" +
                "id='" + id + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgUnitId='" + orgUnitId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
