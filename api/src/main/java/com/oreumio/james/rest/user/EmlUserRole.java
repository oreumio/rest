package com.oreumio.james.rest.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <pre>
 * 메일 사용자 역할
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_USER_ROLE")
public class EmlUserRole implements Serializable {

	/**
	 * 사용자 역할 아이디
	 */
	@Id
	@Column(name = "USER_ROLE_ID", nullable = false, length = 30)
	private String id;

	/**
	 * 사용자 아이디
	 */
	@Column(name = "USER_ID", nullable = false, length = 30)
	private String userId;

	/**
	 * 역할
	 */
	@Column(name = "USER_ROLE", nullable = false)
	private String roleId;

	/**
	 * Constructor.
	 */
	public EmlUserRole() {
	}

    public EmlUserRole(EmlUserRoleVo userRoleVo) {
        this();
        userId = userRoleVo.getUserId();
        roleId = userRoleVo.getRoleId();
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "EmlUserRole{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
