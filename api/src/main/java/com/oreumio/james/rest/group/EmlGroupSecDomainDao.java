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
public class EmlGroupSecDomainDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlGroupSecDomainDao.class);

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
    public List<EmlGroupSecDomain> list(String groupId, String groupDomain) {
        List<EmlGroupSecDomain> groupSecDomainList = em.createQuery("SELECT groupSecDomain FROM EmlGroupSecDomain groupSecDomain WHERE groupSecDomain.groupId = :groupId AND groupSecDomain.groupDomain = :groupDomain", EmlGroupSecDomain.class)
                .setParameter("groupId", groupId)
                .setParameter("groupDomain", groupDomain)
                .getResultList();
        return groupSecDomainList;
    }

    public EmlGroupSecDomain insert(String groupId, String groupDomain, String groupSecDomain) {
        EmlGroupSecDomain emlGroupSecDomain = new EmlGroupSecDomain();
        emlGroupSecDomain.setId("GSD" + 1);
        emlGroupSecDomain.setGroupId(groupId);
        emlGroupSecDomain.setGroupDomain(groupDomain);
        emlGroupSecDomain.setGroupSecDomain(groupSecDomain);
        em.persist(emlGroupSecDomain);
        return emlGroupSecDomain;
    }

    public void delete(String groupId, String groupDomain, String groupSecDomain) {
        EmlGroupSecDomain emlGroupSecDomain = em.createQuery("SELECT groupSecDomain FROM EmlGroupSecDomain groupSecDomain WHERE groupSecDomain.groupId = :groupId AND groupSecDomain.groupDomain = :groupDomain AND groupSecDomain.groupSecDomain = :groupSecDomain", EmlGroupSecDomain.class)
                .setParameter("groupId", groupId)
                .setParameter("groupDomain", groupDomain)
                .setParameter("groupSecDomain", groupSecDomain)
                .getSingleResult();
        em.remove(emlGroupSecDomain);
    }
}
