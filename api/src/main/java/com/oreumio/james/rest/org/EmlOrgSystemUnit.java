package com.oreumio.james.rest.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 조직 단위 정의
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_ORG_SYSTEM_UNIT")
public class EmlOrgSystemUnit {

    /**
     * 조직 단위 아이디
     */
    @Id
    @Column(name = "ORG_SYSTEM_UNIT_ID", nullable = false, length = 30)
    private String id;

    @Column(name = "ORG_SYSTEM_ID", nullable = false, length = 30)
    private String orgSystemId;

    @Column(name = "ORG_SYSTEM_DISPLAY_NAME", nullable = false)
    private String name;

    @Column(name = "PARENT_ORG_SYSTEM_UNIT_ID", nullable = false, length = 30)
    private String parentOrgSystemUnitId;

    public EmlOrgSystemUnit() {
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
