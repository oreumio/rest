package com.oreumio.james.rest.org;

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
public class EmlOrgDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlOrgDao.class);

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
	 * 그룹 도메인 정보를 가져온다.
	 * @param groupId 그룹 아이디
	 * @return 그룹 정보
	 */
    public List<EmlOrg> list(String groupId) {
        List<EmlOrg> emlOrgSystemList = em.createQuery("SELECT orgSystem FROM EmlOrg orgSystem WHERE orgSystem.groupId = :groupId", EmlOrg.class)
                .setParameter("groupId", groupId)
                .getResultList();
        return emlOrgSystemList;
    }

    public EmlOrg insert(String groupId, String name) {
        EmlOrg emlOrgSystem = new EmlOrg();
        emlOrgSystem.setId("OS" + idProvider.next());
        emlOrgSystem.setGroupId(groupId);
        emlOrgSystem.setName(name);
        em.persist(emlOrgSystem);
        return emlOrgSystem;
    }

    public EmlOrg select(String groupId, String orgId) {
        EmlOrg emlOrgSystem = em.createQuery("SELECT orgSystem FROM EmlOrg orgSystem WHERE orgSystem.groupId = :groupId AND orgSystem.id = :orgId", EmlOrg.class)
                .setParameter("groupId", groupId)
                .setParameter("orgId", orgId)
                .getSingleResult();
        return emlOrgSystem;
    }

    public void delete(EmlOrg emlOrgSystem) {
        em.remove(emlOrgSystem);
    }
}
