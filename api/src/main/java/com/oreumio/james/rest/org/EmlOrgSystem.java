package com.oreumio.james.rest.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 조직 체계 정의
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_ORG_SYSTEM")
public class EmlOrgSystem {

    /**
     * 조직 체계 아이디
     */
    @Id
    @Column(name = "ORG_SYSTEM_ID", nullable = false, length = 30)
    private String id;

    @Column(name = "GROUP_ID", nullable = false, length = 30)
    private String groupId;

    @Column(name = "ORG_SYSTEM_DISPLAY_NAME", nullable = false)
    private String name;

    public EmlOrgSystem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EmlOrgSystem{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
