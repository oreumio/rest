package com.oreumio.james.rest.org;

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
public class EmlUserOrgDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlUserOrgDao.class);

	@PersistenceContext(unitName = "rest")
	private EntityManager em;

    /**
	 * @param em EntityManager
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
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

    public void insert(EmlUserOrg userOrg) {
        em.persist(userOrg);
    }

    public EmlUserOrg select(String systemUnitId, String memberId) {
        EmlUserOrg emlUserOrg = em.createQuery("SELECT userOrg FROM EmlUserOrg userOrg WHERE userOrg.orgSystemUnitId = :systemUnitId AND userOrg.id = :memberId", EmlUserOrg.class)
                .setParameter("systemUnitId", systemUnitId)
                .setParameter("memberId", memberId)
                .getSingleResult();
        return emlUserOrg;
    }

    public void delete(EmlUserOrg emlUserOrg) {
        em.remove(emlUserOrg);
    }
}
