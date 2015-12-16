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

	/**
	 * 고객 정보를 가져온다.
	 * @param clientId 고객 아이디
	 * @return 그룹 정보
	 */
	public EmlClient selectClient(String clientId) {
		return em.find(EmlClient.class, clientId);
	}

    public List<EmlClient> list() {
        List<EmlClient> clientList = em.createQuery("SELECT client FROM EmlClient client", EmlClient.class)
                .getResultList();
        return clientList;
    }

    public EmlClient insert(String clientName, String clientState, long clientQuota) {
        EmlClient emlClient = new EmlClient();
        emlClient.setId("C" + 1);
        emlClient.setName(clientName);
        emlClient.setState(clientState);
        emlClient.setQuota(clientQuota);
        em.persist(emlClient);
        return emlClient;
    }

    public void delete(String clientId) {
        EmlClient emlClient = em.find(EmlClient.class, clientId);
        em.remove(emlClient);
    }

    public void updateName(String clientId, String clientName) {
        EmlClient emlClient = em.find(EmlClient.class, clientId);
        emlClient.setName(clientName);
    }

    public void updateQuota(String clientId, long clientQuota) {
        EmlClient emlClient = em.find(EmlClient.class, clientId);
        emlClient.setQuota(clientQuota);
    }

    public void updateState(String clientId, String clientState) {
        EmlClient emlClient = em.find(EmlClient.class, clientId);
        emlClient.setState(clientState);
    }

    public EmlGroupConfigVo selectGroupConfig(String groupId) {
        EmlClient group = em.find(EmlClient.class, groupId);
		return null;
	}
}
