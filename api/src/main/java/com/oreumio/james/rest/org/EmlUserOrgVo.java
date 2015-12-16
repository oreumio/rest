package com.oreumio.james.rest.org;

/**
 * 사용자 조직
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlUserOrgVo {

    private String id;

    private String orgSystemId;

    private String orgSystemUnitId;

    private String userId;

    public EmlUserOrgVo() {
    }

    public EmlUserOrgVo(EmlUserOrg emlUserOrg) {
        id = emlUserOrg.getId();
        orgSystemId = emlUserOrg.getOrgSystemId();
        orgSystemUnitId = emlUserOrg.getOrgSystemUnitId();
        userId = emlUserOrg.getUserId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgSystemId() {
        return orgSystemId;
    }

    public void setOrgSystemId(String orgSystemId) {
        this.orgSystemId = orgSystemId;
    }

    public String getOrgSystemUnitId() {
        return orgSystemUnitId;
    }

    public void setOrgSystemUnitId(String orgSystemUnitId) {
        this.orgSystemUnitId = orgSystemUnitId;
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
                ", orgSystemId='" + orgSystemId + '\'' +
                ", orgSystemUnitId='" + orgSystemUnitId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
