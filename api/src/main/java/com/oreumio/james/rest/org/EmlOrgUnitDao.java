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
public class EmlOrgUnitDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlOrgUnitDao.class);

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
	 * 조직 체계의 조직단위 정보를 가져온다.
	 * @param orgSystemId 조직 체계 아이디
	 * @return 그룹 정보
	 */
    public List<EmlOrgUnit> list(String orgSystemId) {
        List<EmlOrgUnit> emlOrgSystemUnitList = em.createQuery("SELECT orgSystemUnit FROM EmlOrgUnit orgSystemUnit WHERE orgSystemUnit.orgSystemId = :orgSystemId", EmlOrgUnit.class)
                .setParameter("orgSystemId", orgSystemId)
                .getResultList();
        return emlOrgSystemUnitList;
    }

    /**
     * 조직 체계의 조직단위 정보를 가져온다.
     * @param orgSystemUnitId 조직 체계 단위 아이디
     * @return 그룹 정보
     */
    public List<EmlOrgUnit> listSubSystemUnits(String orgSystemUnitId) {
        List<EmlOrgUnit> emlOrgSystemUnitList = em.createQuery("SELECT orgSystemUnit FROM EmlOrgUnit orgSystemUnit WHERE orgSystemUnit.parentOrgSystemUnitId = :orgSystemUnitId", EmlOrgUnit.class)
                .setParameter("orgSystemUnitId", orgSystemUnitId)
                .getResultList();
        return emlOrgSystemUnitList;
    }

    public EmlOrgUnit insert(String orgId, String name, String parentOrgUnitId) {
        EmlOrgUnit emlOrgSystemUnit = new EmlOrgUnit();
        emlOrgSystemUnit.setId("OSU" + idProvider.next());
        emlOrgSystemUnit.setOrgSystemId(orgId);
        emlOrgSystemUnit.setName(name);
        emlOrgSystemUnit.setParentOrgSystemUnitId(parentOrgUnitId);
        em.persist(emlOrgSystemUnit);
        return emlOrgSystemUnit;
    }

    public EmlOrgUnit select(String orgId, String orgUnitId) {
        EmlOrgUnit emlOrgSystemUnit = em.createQuery("SELECT orgSystemUnit FROM EmlOrgUnit orgSystemUnit WHERE orgSystemUnit.orgSystemId = :orgId AND orgSystemUnit.id = :orgUnitId", EmlOrgUnit.class)
                .setParameter("orgId", orgId)
                .setParameter("orgUnitId", orgUnitId)
                .getSingleResult();
        return emlOrgSystemUnit;
    }

    public void delete(EmlOrgUnit emlOrgSystemUnit) {
        em.remove(emlOrgSystemUnit);
    }
}
