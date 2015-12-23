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
    public List<EmlGroupSecDomain> list(String groupId, String groupDomainId) {
        List<EmlGroupSecDomain> groupSecDomainList = em.createQuery("SELECT groupSecDomain FROM EmlGroupSecDomain groupSecDomain WHERE groupSecDomain.groupId = :groupId AND groupSecDomain.groupDomainId = :groupDomainId", EmlGroupSecDomain.class)
                .setParameter("groupId", groupId)
                .setParameter("groupDomainId", groupDomainId)
                .getResultList();
        return groupSecDomainList;
    }

    public EmlGroupSecDomain insert(String groupId, String groupDomainId, String groupSecDomainName) {
        EmlGroupSecDomain emlGroupSecDomain = new EmlGroupSecDomain();
        emlGroupSecDomain.setId("GSD" + idProvider.next());
        emlGroupSecDomain.setGroupId(groupId);
        emlGroupSecDomain.setGroupDomainId(groupDomainId);
        emlGroupSecDomain.setGroupSecDomainName(groupSecDomainName);
        em.persist(emlGroupSecDomain);
        return emlGroupSecDomain;
    }

    public EmlGroupSecDomain select(String groupId, String groupDomainId, String groupSecDomainId) {
        EmlGroupSecDomain groupSecDomain = em.createQuery("SELECT groupSecDomain FROM EmlGroupSecDomain groupSecDomain WHERE groupSecDomain.groupId = :groupId AND groupSecDomain.groupDomainId = :groupDomainId AND groupSecDomain.id = :groupSecDomainId", EmlGroupSecDomain.class)
                .setParameter("groupId", groupId)
                .setParameter("groupDomainId", groupDomainId)
                .setParameter("groupSecDomainId", groupSecDomainId)
                .getSingleResult();
        return groupSecDomain;
    }

    public void delete(EmlGroupSecDomain groupSecDomain) {
        em.remove(groupSecDomain);
    }
}
