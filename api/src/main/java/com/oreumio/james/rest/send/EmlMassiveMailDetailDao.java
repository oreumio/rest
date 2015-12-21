package com.oreumio.james.rest.send;

import com.oreumio.james.util.IdProvider;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Repository
public class EmlMassiveMailDetailDao {

    @PersistenceContext(unitName = "rest")
    private EntityManager em;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    public void insert(EmlMassiveMailDetail emlMassiveMailDetail) {
        em.persist(emlMassiveMailDetail);
    }
}
