package com.oreumio.james.rest.form;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Repository
public class EmlMailFormDao {

    @PersistenceContext(unitName = "rest")
    private EntityManager em;

    /**
     * @param em EntityManager
     */
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public List<EmlMailForm> list(String userId) {
        List<EmlMailForm> emlMailFormList = em.createQuery("SELECT mailForm FROM EmlMailForm mailForm WHERE mailForm.userId = :userId", EmlMailForm.class)
                .setParameter("userId", userId)
                .getResultList();
        return emlMailFormList;
    }

    public EmlMailForm select(String userId, String mailFormId) {
        EmlMailForm emlMailForm = em.createQuery("SELECT mailForm FROM EmlMailForm mailForm WHERE mailForm.userId = :userId and mailForm.id = :mailFormId", EmlMailForm.class)
                .setParameter("userId", userId)
                .setParameter("mailFormId", mailFormId)
                .getSingleResult();
        return emlMailForm;
    }

    public void insert(EmlMailForm emlMailForm) {
        em.persist(emlMailForm);
    }

    public void delete(EmlMailForm emlMailForm) {
        em.remove(emlMailForm);
    }
}
