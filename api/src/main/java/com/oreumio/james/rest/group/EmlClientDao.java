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
public class EmlClientDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlClientDao.class);

	@PersistenceContext(unitName = "rest")
	private EntityManager em;

	/**
	 * @param em EntityManager
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * 고객 정보를 가져온다.
	 * @param clientId 고객 아이디
	 * @return 그룹 정보
	 */
	public EmlClient selectClient(String clientId) {
		return em.find(EmlClient.class, clientId);
	}

    public EmlClient selectClient(String userName, String host) {
        EmlClient client = em.createQuery("SELECT client FROM EmlClient client WHERE client.userName = :userName", EmlClient.class)
                .setParameter("userName", userName)
                .getSingleResult();
        return client;
    }

    public List<EmlClient> list() {
        List<EmlClient> clientList = em.createQuery("SELECT client FROM EmlClient client", EmlClient.class)
                .getResultList();
        return clientList;
    }

    public void insert(EmlClient client) {
        em.persist(client);
    }

    public EmlClient select(String clientId) {
        EmlClient client = em.find(EmlClient.class, clientId);
        return client;
    }

    public void delete(EmlClient client) {
        em.remove(client);
    }
}
