package com.oreumio.james.rest.org;

/**
 * 조직 체계 정의
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlOrgSystemVo {

    /**
     * 조직 체계 아이디
     */
    private String id;

    private String groupId;

    private String name;

    public EmlOrgSystemVo() {
    }

    public EmlOrgSystemVo(EmlOrgSystem emlOrgSystem) {
        id = emlOrgSystem.getId();
        groupId = emlOrgSystem.getGroupId();
        name = emlOrgSystem.getName();
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
        return "EmlOrgSystemVo{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
