package com.oreumio.james.rest.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 메일 사용자
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_USER")
public class EmlUser implements Serializable {

	private static final long serialVersionUID = -8773034075047411223L;

	/**
	 * 메일 유저 아이디
	 */
	@Id
	@Column(name = "USER_ID", nullable = false, length = 30)
	private String id;

	/**
	 * 고객 아이디
	 */
	@Column(name = "GROUP_ID", nullable = false, length = 30)
	private String groupId;

	/**
	 * 유저 아이디
	 */
	@Column(name = "USER_NAME", nullable = false)
	private String userName;

    @Column(name = "DOMAIN_NAME", nullable = false)
    private String host;

    /** Hashed password */
    @Column(name = "PWD", nullable = false, length = 100)
    private String password;

    /**
     * PLAIN, MD5, MD5-B, SHA-256, SHA-256-B, SHA-512, SHA-512-B
     */
    @Column(name = "PWD_HASH_ALGRTH", nullable = false, length = 30)
    private String alg;

    @Column(name = "DISPLAY_NAME", nullable = false)
    private String displayName;

	/**
	 * POP3 &amp;&amp; IMAP 사용여부
	 */
	@Column(name = "POP3_IMAP_USE_YN", nullable = false, length = 1)
	private String socketEnabled;

	/**
	 * 외부 메일 승인 예외자 여부
	 */
	@Column(name = "APPR_MAIL_EXCEPT_YN", nullable = false, length = 1)
	private String apprMailExceptYn;

	/**
	 * 보낸메일,받은메일 자동삭제 예외 여부
	 */
	@Column(name = "MAIL_AUTODEL_EXCEPT_YN", nullable = false, length = 1)
	private String mailAutodelExceptYn;

	/**
	 * 사용자 상태(N:장상,R:중지,D:삭제)
	 */
	@Column(name = "USER_STATE", nullable = false, length = 1)
	private String state;

	/**
	 * 할당 용량
	 */
	@Column(name = "ASGN_CPCTY", nullable = false)
	private long quota;

	/**
	 * 첨부 용량
	 */
	@Column(name = "ATT_CPCTY", nullable = false)
	private long attachmentMaxSize;

	/**
	 * 수정 일시
	 */
	@Column(name = "UPD_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updDt;

	/**
	 * 설정값
	 */
	@Lob
	@Column(name = "USER_SERVER_CONFIG")
	private String serverConfig;

    @Lob
    @Column(name = "USER_CLIENT_CONFIG")
    private String clientConfig;

	/**
	 * 조직도 동기화 시간
	 */
	@Column(name = "ORG_REG_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orgRegDt;

	/**
	 * Constructor.
	 */
	public EmlUser() {
        password = "password";
        alg = "PLAIN";
        socketEnabled = "Y";
        apprMailExceptYn = "N";
        mailAutodelExceptYn = "N";
        serverConfig = "{}";
        clientConfig = "{}";
        state = "R";
	}

    public EmlUser(EmlUserVo emlUserVo) {
        this();
        groupId = emlUserVo.getGroupId();
        userName = emlUserVo.getUserName();
        host = emlUserVo.getHost();
        password = emlUserVo.getPassword();
        displayName = emlUserVo.getDisplayName();
        quota = emlUserVo.getQuota();
        serverConfig = emlUserVo.getServerConfig();
    }

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
	 * @return the socketEnabled
	 */
	public String getSocketEnabled() {
		return socketEnabled;
	}

	/**
	 * @param socketEnabled the socketEnabled to set
	 */
	public void setSocketEnabled(String socketEnabled) {
		this.socketEnabled = socketEnabled;
	}

	/**
	 * @return the apprMailExceptYn
	 */
	public String getApprMailExceptYn() {
		return apprMailExceptYn;
	}

	/**
	 * @param apprMailExceptYn the apprMailExceptYn to set
	 */
	public void setApprMailExceptYn(String apprMailExceptYn) {
		this.apprMailExceptYn = apprMailExceptYn;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the userStatus to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the quota
	 */
	public long getQuota() {
		return quota;
	}

	/**
	 * @param quota the asgnCpcty to set
	 */
	public void setQuota(long quota) {
		this.quota = quota;
	}

	/**
	 * @return the attachmentMaxSize
	 */
	public long getAttachmentMaxSize() {
		return attachmentMaxSize;
	}

	/**
	 * @param attachmentMaxSize the attCpcty to set
	 */
	public void setAttachmentMaxSize(long attachmentMaxSize) {
		this.attachmentMaxSize = attachmentMaxSize;
	}

	/**
	 * @return the updDt
	 */
	public Date getUpdDt() {
		return updDt;
	}

	/**
	 * @param updDt the updDt to set
	 */
	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
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

    /**
	 * @return the orgRegDt
	 */
	public Date getOrgRegDt() {
		return orgRegDt;
	}

	/**
	 * @param orgRegDt the orgRegDt to set
	 */
	public void setOrgRegDt(Date orgRegDt) {
		this.orgRegDt = orgRegDt;
	}

    public String getMailAutodelExceptYn() {
        return mailAutodelExceptYn;
    }

    public void setMailAutodelExceptYn(String mailAutodelExceptYn) {
        this.mailAutodelExceptYn = mailAutodelExceptYn;
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

    /**
	 * 수정시 수정 일시 입력
	 */
	@PreUpdate
	public void setUpdDt() {
		this.updDt = new Date();
	}

    @Override
    public String toString() {
        return "EmlUser{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", userName='" + userName + '\'' +
                ", socketEnabled='" + socketEnabled + '\'' +
                ", apprMailExceptYn='" + apprMailExceptYn + '\'' +
                ", mailAutodelExceptYn='" + mailAutodelExceptYn + '\'' +
                ", state='" + state + '\'' +
                ", quota=" + quota +
                ", attachmentMaxSize=" + attachmentMaxSize +
                ", updDt=" + updDt +
                ", serverConfig='" + serverConfig + '\'' +
                ", clientConfig='" + clientConfig + '\'' +
                ", orgRegDt=" + orgRegDt +
                '}';
    }
}
