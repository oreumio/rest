package com.oreumio.james.rest.group;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <pre>
 * 그룹 엔티티
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_GROUP")
public class EmlGroup implements Serializable {

	/**
	 * 그룹 아이디
	 */
	@Id
	@Column(name = "GROUP_ID", nullable = false, length = 30)
	private String id;

    @Column(name = "CLIENT_ID", nullable = false, length = 30)
    private String clientId;

	@Column(name = "GROUP_DISPLAY_NAME", nullable = false)
	private String name;

    /**
     * 상태(N:정상,R:중지,D:삭제)
     */
    @Column(name = "GROUP_STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "GROUP_QUOTA", nullable = false)
    private long quota;

    /**
     * 설정값
     */
    @Lob
    @Column(name = "GROUP_CONFIG", nullable = false)
    private String config;

	@Column(name = "GROUP_HOST", nullable = false)
	private String host;

	public EmlGroup() {

	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "EmlGroup{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", quota=" + quota +
                ", config='" + config + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
