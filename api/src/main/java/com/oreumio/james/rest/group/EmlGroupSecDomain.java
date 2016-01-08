package com.oreumio.james.rest.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <pre>
 * 그룹 부 도메인 엔티티
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_GROUP_SEC_DOMAIN")
public class EmlGroupSecDomain implements Serializable {

	/**
	 * 고객 아이디
	 */
	@Id
    @Column(name = "GROUP_SEC_DOMAIN_ID", nullable = false, length = 30)
    private String id;

	@Column(name = "GROUP_ID", nullable = false, length = 30)
	private String groupId;

    @Column(name = "GROUP_DOMAIN_ID", nullable = false, length = 30)
    private String groupDomainId;

	@Column(name = "GROUP_SEC_DOMAIN_NAME", nullable = false)
	private String domainName;

    public EmlGroupSecDomain() {
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

    public String getGroupDomainId() {
        return groupDomainId;
    }

    public void setGroupDomainId(String groupDomainId) {
        this.groupDomainId = groupDomainId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public String toString() {
        return "EmlGroupSecDomain{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupDomainId='" + groupDomainId + '\'' +
                ", domainName='" + domainName + '\'' +
                '}';
    }
}
