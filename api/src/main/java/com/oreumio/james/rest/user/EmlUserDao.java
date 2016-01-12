package com.oreumio.james.rest.user;

import org.springframework.stereotype.Repository;

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

    /**
     * @param em EntityManager
     */
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public EmlUser selectUser(String userId) {
        return em.find(EmlUser.class, userId);
    }

    public EmlUser selectUser(String userName, String host) {

        EmlUser emlUser = em.createQuery("SELECT user FROM EmlUser user WHERE user.host = :host AND user.state = :state AND user.userName = :userName", EmlUser.class)
                .setParameter("state", "N")
                .setParameter("userName", userName)
                .setParameter("host", host)
                .getSingleResult();

        return emlUser;
    }

    public long selectMailUsage(String userId) {
        return 0;
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

    public EmlUser select(String groupId, String userId) {
        EmlUser user = em.createQuery("SELECT user FROM EmlUser user WHERE user.id = :userId AND user.groupId = :groupId", EmlUser.class)
                .setParameter("userId", userId)
                .setParameter("groupId", groupId)
                .getSingleResult();
        return user;
    }

    public void insert(EmlUser user) {
        em.persist(user);
    }

    public void delete(EmlUser user) {
        em.remove(user);
    }
}