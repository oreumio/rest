package com.oreumio.james.rest.org;

/**
 * 조직 체계 정의
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlOrgVo {

    /**
     * 조직 체계 아이디
     */
    private String id;

    private String groupId;

    private String displayName;

    public EmlOrgVo() {
    }

    public EmlOrgVo(EmlOrg emlOrgSystem) {
        id = emlOrgSystem.getId();
        groupId = emlOrgSystem.getGroupId();
        displayName = emlOrgSystem.getName();
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "EmlOrgSystemVo{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
