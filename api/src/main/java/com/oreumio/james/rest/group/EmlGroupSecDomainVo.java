package com.oreumio.james.rest.group;

import java.io.Serializable;

/**
 * <pre>
 * 그룹 부 도메인 엔티티
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlGroupSecDomainVo implements Serializable {

	/**
	 * 고객 아이디
	 */
    private String id;

	private String groupId;

    private String groupDomainId;

	private String groupSecDomainName;

    public EmlGroupSecDomainVo() {
    }

    public EmlGroupSecDomainVo(EmlGroupSecDomain groupSecDomain) {
        id = groupSecDomain.getId();
        groupId = groupSecDomain.getGroupId();
        groupDomainId = groupSecDomain.getGroupDomainId();
        groupSecDomainName = groupSecDomain.getGroupSecDomainName();
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

    public String getGroupSecDomainName() {
        return groupSecDomainName;
    }

    public void setGroupSecDomainName(String groupSecDomainName) {
        this.groupSecDomainName = groupSecDomainName;
    }

    @Override
    public String toString() {
        return "EmlGroupSecDomain{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupDomainId='" + groupDomainId + '\'' +
                ", groupSecDomainName='" + groupSecDomainName + '\'' +
                '}';
    }
}
