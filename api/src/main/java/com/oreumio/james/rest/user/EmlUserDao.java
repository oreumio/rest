package com.oreumio.james.rest.user;

import com.oreumio.james.util.IdProvider;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.List;

/**
 * <pre>
 * 메일 사용자 DB 작업
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Repository
public class EmlUserDao {

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

    public EmlUser selectUser(String userId) {
        return em.find(EmlUser.class, userId);
    }

    public EmlUser selectUser(String userName, String domainName) {

        EmlUser emlUser = em.createQuery("SELECT a FROM EmlUser a, EmlGroupDomain b WHERE a.groupId = b.groupId AND a.state = :state AND a.userName = :userName AND b.groupDomain = :domainName", EmlUser.class)
                .setParameter("state", "N")
                .setParameter("userName", userName)
                .setParameter("domainName", domainName.toLowerCase())
                .getSingleResult();

        return emlUser;
    }

    public void deleteUser(String userName, String domainName) {

        EmlUser emlUser = em.createQuery("SELECT a FROM EmlUser a, EmlGroupDomain b WHERE a.groupId = b.groupId AND a.state = :state AND a.userName = :userName AND b.groupDomain = :domainName", EmlUser.class)
                .setParameter("state", "N")
                .setParameter("userName", userName)
                .setParameter("domainName", domainName.toLowerCase())
                .getSingleResult();

        em.remove(emlUser);
    }

    public List<EmlUser> list(String groupId) {
        List<EmlUser> userList = em.createQuery("SELECT user FROM EmlUser user WHERE user.groupId = :groupId", EmlUser.class)
                .setParameter("groupId", groupId)
                .getResultList();
        return userList;
    }

    public EmlUser insert(String userName, String groupId, String userState, long userQuota) {
        EmlUser emlUser = new EmlUser();
        emlUser.setId("U" + idProvider.next());
        emlUser.setUserName(userName);
        emlUser.setGroupId(groupId);
        emlUser.setState(userState);
        emlUser.setQuota(userQuota);
        emlUser.setConfig("{}");
        em.persist(emlUser);
        return emlUser;
    }

    public void delete(String userName, String groupId) {
        EmlUser emlUser = em.createQuery("SELECT user FROM EmlUser user WHERE user.userName = :userName AND user.groupId = :groupId", EmlUser.class)
                .setParameter("userName", userName)
                .setParameter("groupId", groupId)
                .getSingleResult();
        em.remove(emlUser);
    }
}