package com.oreumio.james.rest.group;

import java.io.Serializable;

/**
 * <pre>
 * 고객 도메인 엔티티
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlClientDomainVo implements Serializable {

	/**
	 * 고객 도메인 아이디
	 */
	private String id;

    private String clientId;

	private String clientDomainName;

	public EmlClientDomainVo() {

	}

    public EmlClientDomainVo(EmlClientDomain emlClientDomain) {
        id = emlClientDomain.getId();
        clientId = emlClientDomain.getClientId();
        clientDomainName = emlClientDomain.getDomain();
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

    public String getClientDomainName() {
        return clientDomainName;
    }

    public void setClientDomainName(String clientDomainName) {
        this.clientDomainName = clientDomainName;
    }

    @Override
    public String toString() {
        return "EmlClientDomainVo{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientDomainName='" + clientDomainName + '\'' +
                '}';
    }
}
