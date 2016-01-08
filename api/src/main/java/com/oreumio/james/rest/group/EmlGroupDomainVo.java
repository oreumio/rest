package com.oreumio.james.rest.group;

import java.io.Serializable;

/**
 * <pre>
 * 그룹 도메인 엔티티
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlGroupDomainVo implements Serializable {

	/**
	 * 그룹 도메인 아이디
	 */
	private String id;

    private String groupId;

	private String domainName;

	public EmlGroupDomainVo() {

	}

    public EmlGroupDomainVo(EmlGroupDomain groupDomain) {
        id = groupDomain.getId();
        groupId = groupDomain.getGroupId();
        domainName = groupDomain.getDomainName();
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

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public String toString() {
        return "EmlGroupDomainVo{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", domainName='" + domainName + '\'' +
                '}';
    }
}
