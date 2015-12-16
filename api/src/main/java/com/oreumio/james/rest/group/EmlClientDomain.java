package com.oreumio.james.rest.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <pre>
 * 고객 도메인 엔티티
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_CLIENT_DOMAIN")
public class EmlClientDomain implements Serializable {

	/**
	 * 고객 도메인 아이디
	 */
	@Id
	@Column(name = "ID", nullable = false, length = 30)
	private String id;

    @Column(name = "CLIENT_ID", nullable = false, length = 30)
    private String clientId;

	@Column(name = "DOMAIN_NAME", nullable = false)
	private String domain;

	public EmlClientDomain() {

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "EmlClientDomain{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
