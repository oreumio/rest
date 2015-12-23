package com.oreumio.james.rest.message;

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
public class EmlMailDao {

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

    public List<EmlMail> list(long mailboxId) {
        return em.createQuery("SELECT mail FROM EmlMail mail WHERE mail.mailboxId = :mailboxId")
                .setParameter("mailboxId", mailboxId)
                .getResultList();
    }
}
