package com.oreumio.james.rest.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <pre>
 * 시스템 엔티티
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "EML_SYSTEM")
public class EmlSystem implements Serializable {

	private static final long serialVersionUID = -8874518662432990980L;

	/**
	 * 시스템 고유번호
	 */
	@Id
	@Column(name = "ID", nullable = false, length = 30)
	private String systemId;

	/**
	 * 멀티 도메인
	 */
	@Column(name = "DOMAIN", nullable = false)
	private String domain;

	/**
	 * 도메인
	 */
	@Column(name = "QUOTA", nullable = false)
	private long quota;

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the quota
	 */
	public long getQuota() {
		return quota;
	}

	/**
	 * @param quota the quota to set
	 */
	public void setQuota(long quota) {
		this.quota = quota;
	}

    @Override
    public String toString() {
        return "EmlSystem{" +
                "systemId='" + systemId + '\'' +
                ", domain='" + domain + '\'' +
                ", quota='" + quota + '\'' +
                '}';
    }
}
