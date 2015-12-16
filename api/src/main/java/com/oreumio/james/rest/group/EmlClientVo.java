package com.oreumio.james.rest.group;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlClientVo {
    private String id;
    private String name;
    private String state;
    private long quota;

    public EmlClientVo() {
    }

    public EmlClientVo(EmlClient emlClient) {
        id = emlClient.getId();
        name = emlClient.getName();
        state = emlClient.getState();
        quota = emlClient.getQuota();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getQuota() {
        return quota;
    }

    public void setQuota(long quota) {
        this.quota = quota;
    }

    @Override
    public String toString() {
        return "EmlClientVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", quota=" + quota +
                '}';
    }
}
