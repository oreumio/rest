package com.oreumio.james.rest.user;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * <pre>
 * 메일 사용자 역할
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Repository
public class EmlUserRoleDao {

    @PersistenceContext(unitName = "rest")
    private EntityManager em;

    /**
     * @param em EntityManager
     */
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public List<EmlUserRole> list(String userId) {
        List<EmlUserRole> userRoleList = em.createQuery("SELECT userRole FROM EmlUserRole userRole WHERE userRole.userId = :userId", EmlUserRole.class)
                .setParameter("userId", userId)
                .getResultList();
        return userRoleList;
    }

    public EmlUserRole select(String userId, String roleId) {
        EmlUserRole userRole = em.createQuery("SELECT userRole FROM EmlUserRole userRole WHERE userRole.userId = :userId AND userRole.id = :roleId", EmlUserRole.class)
                .setParameter("userId", userId)
                .setParameter("roleId", roleId)
                .getSingleResult();
        return userRole;
    }

    public void insert(EmlUserRole userRole) {
        em.persist(userRole);
    }

    public void delete(EmlUserRole userRole) {
        em.remove(userRole);
    }
}