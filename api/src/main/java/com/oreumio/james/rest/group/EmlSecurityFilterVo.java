package com.oreumio.james.rest.group;

import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlSecurityFilterVo {
    private int key;
    private String adminUserType;
    private String adminUser;
    private String adminTimeout;
    private List<EmlMailConditionVo> conditionList;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getAdminUserType() {
        return adminUserType;
    }

    public void setAdminUserType(String adminUserType) {
        this.adminUserType = adminUserType;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminTimeout() {
        return adminTimeout;
    }

    public void setAdminTimeout(String adminTimeout) {
        this.adminTimeout = adminTimeout;
    }

    public List<EmlMailConditionVo> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<EmlMailConditionVo> conditionList) {
        this.conditionList = conditionList;
    }

    @Override
    public String toString() {
        return "EmlSecurityFilterVo{" +
                "key='" + key + '\'' +
                ", adminUserType='" + adminUserType + '\'' +
                ", adminUser='" + adminUser + '\'' +
                ", adminTimeout='" + adminTimeout + '\'' +
                ", conditionList=" + conditionList +
                '}';
    }
}
