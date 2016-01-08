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

    public static String DEFAULT_ALG = "PLAIN";

	/**
	 * 그룹 아이디
	 */
	@Id
	@Column(name = "GROUP_ID", nullable = false, length = 30)
	private String id;

    @Column(name = "CLIENT_ID", nullable = false, length = 30)
    private String clientId;

    @Column(name = "GROUP_HOST", nullable = false)
    private String host;

    /**
     * 유저 아이디
     */
    @Column(name = "GROUP_ADMIN_USERNAME", nullable = false)
    private String userName;

    /** Hashed password */
    @Column(name = "GROUP_ADMIN_PWD", nullable = false, length = 100)
    private String password;

    /**
     * PLAIN, MD5, MD5-B, SHA-256, SHA-256-B, SHA-512, SHA-512-B
     */
    @Column(name = "GROUP_ADMIN_PWD_HASH_ALGRTH", nullable = false, length = 30)
    private String alg;

	@Column(name = "GROUP_DISPLAY_NAME", nullable = false)
	private String displayName;

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
    @Column(name = "GROUP_SERVER_CONFIG", nullable = false)
    private String serverConfig;

    @Lob
    @Column(name = "GROUP_CLIENT_CONFIG", nullable = false)
    private String clientConfig;

	public EmlGroup() {
        alg = DEFAULT_ALG;
        state = "R";
        serverConfig = "{}";
        clientConfig = "{}";
	}

    public EmlGroup(EmlGroupVo groupVo) {
        this();
        host = groupVo.getHost();
        userName = groupVo.getUserName();
        password = groupVo.getPassword();
        if (groupVo.getAlg() != null) {
            alg = groupVo.getAlg();
        }
        clientId = groupVo.getClientId();
        displayName = groupVo.getDisplayName();
        quota = groupVo.getQuota();
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(String serverConfig) {
        this.serverConfig = serverConfig;
    }

    public String getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(String clientConfig) {
        this.clientConfig = clientConfig;
    }

    @Override
    public String toString() {
        return "EmlGroup{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", state='" + state + '\'' +
                ", quota=" + quota +
                ", serverConfig='" + serverConfig + '\'' +
                ", clientConfig='" + clientConfig + '\'' +
                ", host='" + host + '\'' +
                ", userName='" + userName + '\'' +
                ", alg='" + alg + '\'' +
                '}';
    }
}
