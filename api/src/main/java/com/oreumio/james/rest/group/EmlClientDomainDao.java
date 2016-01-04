package com.oreumio.james.rest.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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

    /**
	 * @param em EntityManager
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

    public List<EmlClientDomain> list(String clientId) {
        List<EmlClientDomain> clientDomainList = em.createQuery("SELECT clientDomain FROM EmlClientDomain clientDomain WHERE clientDomain.clientId = :clientId", EmlClientDomain.class)
                .setParameter("clientId", clientId)
                .getResultList();
        return clientDomainList;
    }

    public void insert(EmlClientDomain clientDomain) {
        em.persist(clientDomain);
    }

    public EmlClientDomain select(String clientId, String domainId) {
        EmlClientDomain clientDomain = em.createQuery("SELECT clientDomain FROM EmlClientDomain clientDomain WHERE clientDomain.clientId = :clientId AND clientDomain.id = :domainId", EmlClientDomain.class)
                .setParameter("clientId", clientId)
                .setParameter("domainId", domainId)
                .getSingleResult();
        return clientDomain;
    }

    public void delete(EmlClientDomain clientDomain) {
        em.remove(clientDomain);
    }
}
