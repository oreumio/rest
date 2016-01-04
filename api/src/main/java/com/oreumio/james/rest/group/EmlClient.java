package com.oreumio.james.rest.group;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 고객 엔티티
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_CLIENT")
public class EmlClient implements Serializable {

	/**
	 * 고객 아이디
	 */
	@Id
	@Column(name = "CLIENT_ID", nullable = false, length = 30)
	private String id;

    /**
     * 유저 아이디
     */
    @Column(name = "CLIENT_USER_NAME", nullable = false)
    private String userName;

    @Column(name = "CLIENT_HOST", nullable = false)
    private String host;

    /** Hashed password */
    @Column(name = "PWD", nullable = false, length = 100)
    private String password;

    /**
     * PLAIN, MD5, MD5-B, SHA-256, SHA-256-B, SHA-512, SHA-512-B
     */
    @Column(name = "PWD_HASH_ALGRTH", nullable = false, length = 30)
    private String alg;

	@Column(name = "CLIENT_DISPLAY_NAME", nullable = false)
	private String displayName;

	/**
	 * 상태(N:정상,R:중지,D:삭제)
	 */
	@Column(name = "CLIENT_STATE", nullable = false, length = 1)
	private String state;

    @Column(name = "CLIENT_QUOTA", nullable = false)
    private long quota;

    /**
     * 수정 일시
     */
    @Column(name = "UPDATE_TS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTs;

	public EmlClient() {
        password = "password";
        alg = "PLAIN";
	}

    public EmlClient(EmlClientVo clientVo) {
        this();
        userName = clientVo.getUserName();
        host = clientVo.getHost();
        password = clientVo.getPassword();
        alg = clientVo.getAlg();
        displayName = clientVo.getDisplayName();
        state = clientVo.getState();
        quota = clientVo.getQuota();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    @PrePersist
    public void PrePersist() {
        if (updateTs == null) {
            this.updateTs = new Date();
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTs = new Date();
    }

    @Override
    public String toString() {
        return "EmlClient{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", host='" + host + '\'' +
                ", password='" + password + '\'' +
                ", alg='" + alg + '\'' +
                ", displayName='" + displayName + '\'' +
                ", state='" + state + '\'' +
                ", quota=" + quota +
                '}';
    }
}
