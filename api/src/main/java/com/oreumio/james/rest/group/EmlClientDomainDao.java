package com.oreumio.james.rest.group;

import com.oreumio.james.util.IdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * <pre>
 * 회사 설정 DB 작업
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Repository
public class EmlClientDomainDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlClientDomainDao.class);

	@PersistenceContext(unitName = "rest")
	private EntityManager em;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    /**
	 * @param em EntityManager
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

    public void setIdProvider(IdProvider<String> idProvider) {
        this.idProvider = idProvider;
    }

    public List<EmlClientDomain> list(String clientId) {
        List<EmlClientDomain> clientDomainList = em.createQuery("SELECT clientDomain FROM EmlClientDomain clientDomain WHERE clientDomain.clientId = :clientId", EmlClientDomain.class)
                .setParameter("clientId", clientId)
                .getResultList();
        return clientDomainList;
    }

    public EmlClientDomain insert(String clientId, String domain) {
        EmlClientDomain emlClientDomain = new EmlClientDomain();
        emlClientDomain.setId("CD" + 1);
        emlClientDomain.setClientId(clientId);
        emlClientDomain.setDomain(domain);
        em.persist(emlClientDomain);
        return emlClientDomain;
    }

    public void delete(String clientId, String domain) {
        EmlClientDomain clientDomain = em.createQuery("SELECT clientDomain FROM EmlClientDomain clientDomain WHERE clientDomain.clientId = :clientId AND clientDomain.domain = :domain", EmlClientDomain.class)
                .setParameter("clientId", clientId)
                .setParameter("domain", domain)
                .getSingleResult();
        em.remove(clientDomain);
    }
}
