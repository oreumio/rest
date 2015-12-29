package com.oreumio.james.rest.mailbox;

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
public class EmlMailboxDao {

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

    public List<EmlMailbox> list(String userId) {
        return em.createQuery("SELECT mailbox FROM EmlMailbox mailbox WHERE mailbox.userId = :userId", EmlMailbox.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public EmlMailbox select(long mailboxId) {
        return em.find(EmlMailbox.class, mailboxId);
    }

    public EmlMailbox get(String userId, String mailboxName) {
        return em.createQuery("SELECT mailbox FROM EmlMailbox mailbox WHERE mailbox.userId = :userId AND mailbox.name = :mailboxName", EmlMailbox.class)
                .setParameter("userId", userId)
                .setParameter("mailboxName", mailboxName)
                .getSingleResult();
    }

    public void insert(EmlMailbox mailbox) {
        em.persist(mailbox);
    }

    public void delete(EmlMailbox mailbox) {
        em.remove(mailbox);
    }
}
