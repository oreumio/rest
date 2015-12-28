package com.oreumio.james.rest.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 사용자 조직
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_USER_ORG")
public class EmlUserOrg {

    @Id
    @Column(name = "ID", nullable = false, length = 30)
    private String id;

    @Column(name = "ORG_SYSTEM_UNIT_ID", nullable = false, length = 30)
    private String orgSystemUnitId;

    @Column(name = "USER_ID", nullable = false, length = 30)
    private String userId;

    public EmlUserOrg() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                ", orgSystemUnitId='" + orgSystemUnitId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
