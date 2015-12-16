package com.oreumio.james.rest.org;

/**
 * 조직 단위 정의
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlOrgSystemUnitVo {

    /**
     * 조직 단위 아이디
     */
    private String id;

    private String orgSystemId;

    private String name;

    private String parentOrgSystemUnitId;

    public EmlOrgSystemUnitVo() {
    }

    public EmlOrgSystemUnitVo(EmlOrgSystemUnit emlOrgSystemUnit) {
        id = emlOrgSystemUnit.getId();
        orgSystemId = emlOrgSystemUnit.getOrgSystemId();
        name = emlOrgSystemUnit.getName();
        parentOrgSystemUnitId = emlOrgSystemUnit.getParentOrgSystemUnitId();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentOrgSystemUnitId() {
        return parentOrgSystemUnitId;
    }

    public void setParentOrgSystemUnitId(String parentOrgSystemUnitId) {
        this.parentOrgSystemUnitId = parentOrgSystemUnitId;
    }
}
