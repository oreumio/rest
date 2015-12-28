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
public class EmlUserOrgDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlUserOrgDao.class);

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
	 * @param systemUnitId 그룹 아이디
	 * @return 그룹 정보
	 */
    public List<EmlUserOrg> list(String systemUnitId) {
        List<EmlUserOrg> emlUserOrgs = em.createQuery("SELECT userOrg FROM EmlUserOrg userOrg WHERE userOrg.orgSystemUnitId = :systemUnitId", EmlUserOrg.class)
                .setParameter("systemUnitId", systemUnitId)
                .getResultList();
        return emlUserOrgs;
    }

    public EmlUserOrg insert(String orgUnitId, String userId) {
        EmlUserOrg emlUserOrg = new EmlUserOrg();
        emlUserOrg.setId("GSD" + 1);
        emlUserOrg.setOrgSystemUnitId(orgUnitId);
        emlUserOrg.setUserId(userId);
        em.persist(emlUserOrg);
        return emlUserOrg;
    }

    public EmlUserOrg select(String systemUnitId, String userId) {
        EmlUserOrg emlUserOrg = em.createQuery("SELECT userOrg FROM EmlUserOrg userOrg WHERE userOrg.orgSystemUnitId = :systemUnitId AND userOrg.userId = :userId", EmlUserOrg.class)
                .setParameter("systemUnitId", systemUnitId)
                .setParameter("userId", userId)
                .getSingleResult();
        return emlUserOrg;
    }

    public void delete(EmlUserOrg emlUserOrg) {
        em.remove(emlUserOrg);
    }
}
