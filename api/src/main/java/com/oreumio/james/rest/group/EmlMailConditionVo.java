package com.oreumio.james.rest.group;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlMailConditionVo {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EmlMailConditionVo{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
