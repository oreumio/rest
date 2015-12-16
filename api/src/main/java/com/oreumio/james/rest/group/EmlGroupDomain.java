package com.oreumio.james.rest.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <pre>
 * 그룹 도메인 엔티티
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_GROUP_DOMAIN")
public class EmlGroupDomain implements Serializable {

	/**
	 * 그룹 도메인 아이디
	 */
	@Id
	@Column(name = "GROUP_DOMAIN_ID", nullable = false, length = 30)
	private String id;

    @Column(name = "GROUP_ID", nullable = false, length = 30)
    private String groupId;

	@Column(name = "GROUP_DOMAIN_NAME", nullable = false)
	private String groupDomain;

	public EmlGroupDomain() {

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

    public String getGroupDomain() {
        return groupDomain;
    }

    public void setGroupDomain(String groupDomain) {
        this.groupDomain = groupDomain;
    }

    @Override
    public String toString() {
        return "EmlGroupDomain{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupDomain='" + groupDomain + '\'' +
                '}';
    }
}
