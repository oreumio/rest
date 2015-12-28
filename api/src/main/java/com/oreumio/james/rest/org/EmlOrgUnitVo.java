package com.oreumio.james.rest.org;

/**
 * 조직 단위 정의
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlOrgUnitVo {

    /**
     * 조직 단위 아이디
     */
    private String id;

    private String orgId;

    private String displayName;

    private String parentOrgUnitId;

    public EmlOrgUnitVo() {
    }

    public EmlOrgUnitVo(EmlOrgUnit emlOrgSystemUnit) {
        id = emlOrgSystemUnit.getId();
        orgId = emlOrgSystemUnit.getOrgSystemId();
        displayName = emlOrgSystemUnit.getName();
        parentOrgUnitId = emlOrgSystemUnit.getParentOrgSystemUnitId();
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getParentOrgUnitId() {
        return parentOrgUnitId;
    }

    public void setParentOrgUnitId(String parentOrgUnitId) {
        this.parentOrgUnitId = parentOrgUnitId;
    }

    @Override
    public String toString() {
        return "EmlOrgSystemUnitVo{" +
                "id='" + id + '\'' +
                ", orgId='" + orgId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", parentOrgUnitId='" + parentOrgUnitId + '\'' +
                '}';
    }
}
