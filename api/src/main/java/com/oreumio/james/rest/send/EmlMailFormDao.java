package com.oreumio.james.rest.send;

import com.oreumio.james.util.IdProvider;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
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

    public List<EmlMailForm> list(String userId) {
        List<EmlMailForm> emlMailFormList = em.createQuery("SELECT mailForm FROM EmlMailForm mailForm WHERE mailForm.userId = :userId", EmlMailForm.class)
                .setParameter("userId", userId)
                .getResultList();
        return emlMailFormList;
    }

    public EmlMailForm insert(EmlMailFormVo emlMailFormVo) {
        EmlMailForm emlMailForm = new EmlMailForm(emlMailFormVo);
        em.persist(emlMailForm);
        return emlMailForm;
    }

    public EmlMailForm select(String mailFormId) {
        EmlMailForm emlMailForm = em.find(EmlMailForm.class, mailFormId);
        return emlMailForm;
    }

    public void delete(EmlMailForm emlMailForm) {
        em.remove(emlMailForm);
    }
}
