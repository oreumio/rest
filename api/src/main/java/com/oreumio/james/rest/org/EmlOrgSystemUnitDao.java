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
public class EmlOrgSystemUnitDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlOrgSystemUnitDao.class);

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
    public List<EmlOrgSystemUnit> list(String orgSystemId) {
        List<EmlOrgSystemUnit> emlOrgSystemUnitList = em.createQuery("SELECT orgSystemUnit FROM EmlOrgSystemUnit orgSystemUnit WHERE orgSystemUnit.orgSystemId = :orgSystemId", EmlOrgSystemUnit.class)
                .setParameter("orgSystemId", orgSystemId)
                .getResultList();
        return emlOrgSystemUnitList;
    }

    /**
     * 조직 체계의 조직단위 정보를 가져온다.
     * @param orgSystemUnitId 조직 체계 단위 아이디
     * @return 그룹 정보
     */
    public List<EmlOrgSystemUnit> listSubSystemUnits(String orgSystemUnitId) {
        List<EmlOrgSystemUnit> emlOrgSystemUnitList = em.createQuery("SELECT orgSystemUnit FROM EmlOrgSystemUnit orgSystemUnit WHERE orgSystemUnit.parentOrgSystemUnitId = :orgSystemUnitId", EmlOrgSystemUnit.class)
                .setParameter("orgSystemUnitId", orgSystemUnitId)
                .getResultList();
        return emlOrgSystemUnitList;
    }

    public EmlOrgSystemUnit insert(String orgSystemId, String name, String parentOrgSystemUnitId) {
        EmlOrgSystemUnit emlOrgSystemUnit = new EmlOrgSystemUnit();
        emlOrgSystemUnit.setId("OSU" + idProvider.next());
        emlOrgSystemUnit.setOrgSystemId(orgSystemId);
        emlOrgSystemUnit.setName(name);
        emlOrgSystemUnit.setParentOrgSystemUnitId(parentOrgSystemUnitId);
        em.persist(emlOrgSystemUnit);
        return emlOrgSystemUnit;
    }

    public EmlOrgSystemUnit select(String orgSystemId, String name) {
        EmlOrgSystemUnit emlOrgSystemUnit = em.createQuery("SELECT orgSystemUnit FROM EmlOrgSystemUnit orgSystemUnit WHERE orgSystemUnit.orgSystemId = :orgSystemId AND orgSystemUnit.name = :name", EmlOrgSystemUnit.class)
                .setParameter("orgSystemId", orgSystemId)
                .setParameter("name", name)
                .getSingleResult();
        return emlOrgSystemUnit;
    }

    public void delete(EmlOrgSystemUnit emlOrgSystemUnit) {
        em.remove(emlOrgSystemUnit);
    }
}
