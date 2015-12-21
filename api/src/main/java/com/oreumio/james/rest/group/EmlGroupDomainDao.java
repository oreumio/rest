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
public class EmlGroupDomainDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlGroupDomainDao.class);

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
    public List<EmlGroupDomain> list(String groupId) {
        List<EmlGroupDomain> groupDomainList = em.createQuery("SELECT groupDomain FROM EmlGroupDomain groupDomain WHERE groupDomain.groupId = :groupId", EmlGroupDomain.class)
                .setParameter("groupId", groupId)
                .getResultList();
        return groupDomainList;
    }

    public EmlGroupDomain insert(String groupId, String groupDomain) {
        EmlGroupDomain emlGroupDomain = new EmlGroupDomain();
        emlGroupDomain.setId("GD" + idProvider.next());
        emlGroupDomain.setGroupId(groupId);
        emlGroupDomain.setGroupDomain(groupDomain);
        em.persist(emlGroupDomain);
        return emlGroupDomain;
    }

    public void delete(String groupId, String groupDomain) {
        EmlGroupDomain emlGroupDomain = em.createQuery("SELECT groupDomain FROM EmlGroupDomain groupDomain WHERE groupDomain.groupId = :groupId AND groupDomain.groupDomain = :groupDomain", EmlGroupDomain.class)
                .setParameter("groupId", groupId)
                .setParameter("groupDomain", groupDomain)
                .getSingleResult();
        em.remove(emlGroupDomain);
    }
}
