package com.oreumio.james.rest.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * <pre>
 * 최근 사용한 메일 주소 DB 처리
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Repository
public class EmlRecentlyUsedRecipientDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlRecentlyUsedRecipientDao.class);

	@PersistenceContext(unitName = "rest")
	private EntityManager em;

	/**
	 * @param em EntityManager
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * 최근 사용한 수신자 메일 주소 입력
	 * @param emlRecentUseEmail 최근 사용한 수신자 메일 주소 domain
	 */
	public void persist(EmlRecentlyUsedRecipient emlRecentUseEmail) {
		em.persist(emlRecentUseEmail);
	}

	/**
	 * 최근 사용한 주소 목록
	 *
	 * @param userId 사용자 아이디
	 * @return 메일 목록
	 */
	public List<EmlRecentlyUsedRecipient> listRecentlyUsedRecipients(String userId) {
		String sqlStr = "SELECT a FROM EmlRecentlyUsedRecipient a WHERE a.userId = :userId ORDER BY a.regDt DESC";

		TypedQuery<EmlRecentlyUsedRecipient> query = em.createQuery(sqlStr, EmlRecentlyUsedRecipient.class)
				.setParameter("userId", userId); //이메일 아이디

		return query.getResultList();
	}

	/**
	 * 최근 사용한 주소 삭제
	 *
	 * @param userId 이메일 아이디
	 * @param mailTo 수신자 메일 주소
	 */
	public void deleteRecentlyUsedRecipient(String userId, String mailTo) {
		Query query = em.createQuery("DELETE FROM EmlRecentlyUsedRecipient r WHERE r.userId = :userId AND r.mailTo = :mailTo")
				.setParameter("userId", userId)
				.setParameter("mailTo", mailTo);

		query.executeUpdate();
	}

    public void delete(EmlRecentlyUsedRecipient emlRecentlyUsedRecipient) {
        em.remove(emlRecentlyUsedRecipient);
    }

    /**
	 * 메일 주소의 등록일을 현재시간으로 변경한다.
	 *
	 * @param userId 이메일 아이디
	 * @param mailTo 수신자 메일 주소
	 */
	public void updateRecentlyUsedRecipientRegDt(String userId, String mailTo) {
		Query query = em.createQuery("UPDATE EmlRecentlyUsedRecipient r SET r.regDt = CURRENT_TIMESTAMP WHERE r.userId = :userId AND r.mailTo = :mailTo")
				.setParameter("userId", userId)
				.setParameter("mailTo", mailTo);

		query.executeUpdate();
	}
}
