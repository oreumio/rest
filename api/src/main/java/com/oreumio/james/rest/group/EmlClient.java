package com.oreumio.james.rest.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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

	@Column(name = "CLIENT_DISPLAY_NAME", nullable = false)
	private String name;

	/**
	 * 상태(N:정상,R:중지,D:삭제)
	 */
	@Column(name = "CLIENT_STATE", nullable = false, length = 1)
	private String state;

    @Column(name = "CLIENT_QUOTA", nullable = false)
    private long quota;

	public EmlClient() {

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
        return "EmlClient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", quota=" + quota +
                '}';
    }
}
