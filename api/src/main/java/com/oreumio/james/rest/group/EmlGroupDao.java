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
public class EmlGroupDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlGroupDao.class);

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
	 * 그룹 정보를 가져온다.
	 * @param groupId 그룹 아이디
	 * @return 그룹 정보
	 */
	public EmlGroup selectGroup(String groupId) {
		return em.find(EmlGroup.class, groupId);
	}

    public List<EmlGroup> list(String clientId) {
        List<EmlGroup> groupList = em.createQuery("SELECT group FROM EmlGroup group WHERE group.clientId = :clientId", EmlGroup.class)
                .setParameter("clientId", clientId)
                .getResultList();
        return groupList;
    }

    public EmlGroup insert(String groupName, String clientId, String groupState, long groupQuota) {
        EmlGroup emlGroup = new EmlGroup();
        emlGroup.setId("G" + 1);
        emlGroup.setName(groupName);
        emlGroup.setClientId(clientId);
        emlGroup.setState(groupState);
        emlGroup.setQuota(groupQuota);
        emlGroup.setConfig("{}");
        emlGroup.setHost("oreumio.com");
        em.persist(emlGroup);
        return emlGroup;
    }

    public void delete(String groupId) {
        EmlGroup emlGroup = em.find(EmlGroup.class, groupId);
        em.remove(emlGroup);
    }

    public void updateName(String groupId, String groupName) {
        EmlGroup emlGroup = em.find(EmlGroup.class, groupId);
        emlGroup.setName(groupName);
    }

    public void updateQuota(String groupId, long groupQuota) {
        EmlGroup emlGroup = em.find(EmlGroup.class, groupId);
        emlGroup.setQuota(groupQuota);
    }

    public void updateState(String groupId, String groupState) {
        EmlGroup emlGroup = em.find(EmlGroup.class, groupId);
        emlGroup.setState(groupState);
    }

	public EmlGroupConfigVo selectGroupConfig(String groupId) {
        EmlGroup group = em.find(EmlGroup.class, groupId);
		return null;
	}
}
