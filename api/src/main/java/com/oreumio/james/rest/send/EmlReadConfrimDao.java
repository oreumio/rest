package com.oreumio.james.rest.send;

import com.oreumio.james.rest.message.EmlMailVo;
import com.oreumio.james.rest.message.EmlMailSearchVo;
import com.oreumio.james.user.model.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * 수신 확인 상세 DB 작업
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Repository
public class EmlReadConfrimDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlReadConfrimDao.class);

	@PersistenceContext(unitName = "rest")
	private EntityManager em;

	/**
	 * @param em EntityManager
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * 수신 확인 상세 목록 총 갯수를 가져온다.
	 * @param emlSearchVo 수신확인 상세 vo
	 * @return 수신 확인 상세 목록 총 갯수
	 */
	public long selectConfirmDetailCount(EmlMailSearchVo<EmlReadConfirmVo> emlSearchVo) {
        List<EmlReadConfirmVo> emlReadConfirmVo = emlSearchVo.getResult();

		String queryStr = "SELECT COUNT(c.confirmId) FROM EmlReadConfirm c WHERE"
				+ " c.emlReadConfirmMail.confirmMailId = :confirmMailId ";

		if (!StringUtils.isEmpty(emlSearchVo.getKeyword())) { //검색어가 있다면
			queryStr += "AND c.mailTo LIKE :keyword ";
		}

		//전체:all, 안읽음:unread, 읽음:read, 내부로 발송:in, 외부로 발송:out
		if ("unread".equals(emlSearchVo.getSearchField())) {
			queryStr += "AND c.readCnt = 0 ";
		} else if ("read".equals(emlSearchVo.getSearchField())) {
			queryStr += "AND c.readCnt > 0 ";
		} else if ("in".equals(emlSearchVo.getSearchField())) {
			queryStr += "AND c.mailToType = 'I' ";
		} else if ("out".equals(emlSearchVo.getSearchField())) {
			queryStr += "AND c.mailToType = 'E' ";
		}

		Query queryTotal = em.createQuery(queryStr);
/*
		queryTotal.setParameter("confirmMailId", emlReadConfirmVo.getConfirmMailId()); //수신확인 부모키
*/
		if (StringUtils.contains(queryStr, ":keyword")) {
			queryTotal.setParameter("keyword", "%" + emlSearchVo.getKeyword() + "%"); //검색어
		}
		return (Long) queryTotal.getSingleResult();
	}

	/**
	 * 수신 확인 상세 목록을 가져온다.
	 * @param emlSearchVo 수신확인 상세 vo
	 * @return 수신 확인 상세 목록
	 */
	public List<EmlReadConfirmVo> selectConfirmDetailList(EmlMailSearchVo<EmlReadConfirmVo> emlSearchVo) {
        List<EmlReadConfirmVo> emlReadConfirmVo = emlSearchVo.getResult();

		String queryStr = "SELECT NEW " + EmlReadConfirmVo.class.getName() + "(c.confirmId,"
				+ " c.mailTo, c.mailToType, c.frstReadDt, c.lastReadDt, c.readCnt, c.procStatus)"
				+ " FROM EmlReadConfirm c"
				+ " WHERE c.emlReadConfirmMail.confirmMailId = :confirmMailId ";

		if (!StringUtils.isEmpty(emlSearchVo.getKeyword())) { //검색어가 있다면
			queryStr += "AND c.mailTo LIKE :keyword ";
		}

		//전체:all, 안읽음:unread, 읽음:read, 내부로 발송:in, 외부로 발송:out
		if ("unread".equals(emlSearchVo.getSearchField())) {
			queryStr += "AND c.readCnt = 0 ";
		} else if ("read".equals(emlSearchVo.getSearchField())) {
			queryStr += "AND c.readCnt > 0 ";
		} else if ("in".equals(emlSearchVo.getSearchField())) {
			queryStr += "AND c.mailToType = 'I' ";
		} else if ("out".equals(emlSearchVo.getSearchField())) {
			queryStr += "AND c.mailToType = 'E' ";
		}

		String orderByStr = "ORDER BY c.mailTo ";
		orderByStr += emlSearchVo.getOrderType();

		TypedQuery<EmlReadConfirmVo> query = em.createQuery(queryStr + orderByStr,
				EmlReadConfirmVo.class);
/*
		query.setParameter("confirmMailId", emlReadConfirmVo.getConfirmMailId()); //수신확인 부모키
*/
		if (StringUtils.contains(queryStr, ":keyword")) {
			query.setParameter("keyword", "%" + emlSearchVo.getKeyword() + "%"); //검색어
		}
		int pageNo = emlSearchVo.getPageNo();
		int pageSize = emlSearchVo.getPageSize();
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	/**
	 * 수신 확인 상세 정보 한 건을 가져온다.
	 * @param confirmId 수신확인 상세 아이디
	 * @return 수신 확인 상세 정보
	 */
	public EmlReadConfirm selectConfirmDetail(String confirmId) {
		return em.find(EmlReadConfirm.class, confirmId);
	}

	/**
	 * 수신 확인 상세 정보 한 건을 가져온다.
	 * @param confirmMailId 수신확인 상세 아이디
	 * @param userId 이메일 아이디
	 * @return 수신 확인 상세 정보
	 */
	public EmlReadConfirm selectConfirmDetail(String confirmMailId, String userId) {
		TypedQuery<EmlReadConfirm> query = em.createQuery("SELECT c FROM EmlReadConfirm c"
				+ " JOIN FETCH c.emlReadConfirmMail m"
				+ " WHERE m.confirmMailId = :confirmMailId AND c.mailToEmailId = :userId",
				EmlReadConfirm.class)
				.setParameter("confirmMailId", confirmMailId) //수신확인 메일 아이디
				.setParameter("userId", userId); //이메일 아이디

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * 내부 메일 수신자중에 읽지 않은 수신자 수를 가져온다.
	 * @param confirmMailId 수신 확인 메일 아아디
	 * @return 수내부 메일 수신자중에 읽지 않은 수신자 수
	 */
	public long selectUnreadConfirmDetailCount(String confirmMailId) {
		Query queryTotal = em.createQuery("SELECT COUNT(c.confirmId) FROM EmlReadConfirm c WHERE"
				+ " c.emlReadConfirmMail.confirmMailId = :confirmMailId AND c.procStatus ='N' AND c.mailToType='I'")
				.setParameter("confirmMailId", confirmMailId); //수신확인 부모키

		return (Long) queryTotal.getSingleResult();
	}

	/**
	 * 수신 확인 부모 아이디로 메일 정보 목록을 가져온다.
	 * @param confirmMailId 수신확인 메일 아이디
	 * @return 메일 정보 목록
	 */
	public List<EmlMailVo> selectConfirmDetailMailList(String confirmMailId) {
		TypedQuery<EmlMailVo> query = em.createQuery("SELECT "
				+ " NEW " + EmlMailVo.class.getName() + "(u.userId,"
				+ " b.mailboxPath, m.uid, m.charSet,"
				+ " u.clientId) FROM EmlReadConfirm c, IN(c.emlMails) m JOIN m.emlMailbox b JOIN b.emlUser u"
				+ " WHERE c.emlReadConfirmMail.confirmMailId = :confirmMailId AND c.procStatus='N'"
				+ " AND c.mailToType = 'I' AND m.delYn = 'N' AND u.state <> :state ORDER BY u.userId"
				+ " ASC", EmlMailVo.class)
				.setParameter("state", User.DELETED)
				.setParameter("confirmMailId", confirmMailId); //메세지 아이디

		return query.getResultList();
	}

	/**
	 * 내부 메일을 모두 회수 처리한다.
	 * @param confirmMailId 수신 확인 메일 아아디
	 */
	public void updateAllConfirmDetailRecoverStatus(String confirmMailId) {
		Query query = em.createQuery("UPDATE EmlReadConfirm c SET c.procStatus = 'R'"
				+ " WHERE c.emlReadConfirmMail.confirmMailId = :confirmMailId AND c.procStatus ='N'"
				+ " AND c.mailToType='I'")
				.setParameter("confirmMailId", confirmMailId); //수신확인 부모키

		query.executeUpdate();
	}

	/**
	 * 수신 확인 자식 아이디로 메일 정보 목록을 가져온다.
	 * @param confirmId 수신확인 상세 아이디
	 * @return 수신자의 메일 정보 목록
	 */
	public List<EmlMailVo> selectChildConfirmDetailMailList(String confirmId) {
		TypedQuery<EmlMailVo> query = em.createQuery("SELECT "
				+ " NEW " + EmlMailVo.class.getName() + "(u.userId,"
				+ " b.mailboxPath, m.uid, m.charSet,"
				+ " u.clientId) FROM EmlReadConfirm c, IN(c.emlMails) m"
				+ " JOIN m.emlMailbox b JOIN b.emlUser u"
				+ " WHERE c.confirmId = :confirmId AND c.procStatus='N' AND c.mailToType = 'I'"
				+ " AND u.state <> :state"
				+ " AND m.delYn = 'N' AND u.userId = c.mailToEmailId", EmlMailVo.class)
				.setParameter("confirmId", confirmId) //수신확인 상세 아이디
				.setParameter("state", User.DELETED);

		return query.getResultList();
	}

	/**
	 * 수신 확인 자식 아이디로 메일 정보 목록을 가져온다.
	 * @param confirmIdArr 수신확인 상세 아이디 목록
	 * @return 수신자의 메일 정보 목록
	 */
	public List<EmlMailVo> selectChildConfirmDetailMailList(String []confirmIdArr) {
		TypedQuery<EmlMailVo> query = em.createQuery("SELECT "
				+ " NEW " + EmlMailVo.class.getName() + "(u.userId,"
				+ " b.mailboxPath, m.uid, m.charSet,"
				+ " u.clientId) FROM EmlReadConfirm c, IN(c.emlMails) m"
				+ " JOIN m.emlMailbox b JOIN b.emlUser u"
				+ " WHERE c.confirmId IN (:confirmIdArr) AND c.procStatus='N' AND c.mailToType = 'I'"
				+ " AND u.userStatus <> 'D'"
				+ " AND m.delYn = 'N' AND u.userId = c.mailToEmailId", EmlMailVo.class);
		query.setParameter("confirmIdArr", Arrays.asList(confirmIdArr)); //수신확인 상세 아이디
		return query.getResultList();
	}

	/**
	 * 수신확인 상세 삭제
	 * @param confirmMailIdList 수신확인 부모 고유 번호 목록
	 */
	public void deleteReadConfirmDetail(List<String> confirmMailIdList) {
		Query query = em.createQuery("DELETE FROM EmlReadConfirm m"
				+ " WHERE m.emlReadConfirmMail.confirmMailId IN :confirmMailIdList");

		query.setParameter("confirmMailIdList", confirmMailIdList);
		query.executeUpdate();
	}

	/**
	 * 수신확인 부모 삭제
	 * @param confirmMailIdList 수신확인 부모 고유 번호 목록
	 */
	public void deleteReadConfirm(List<String> confirmMailIdList) {
		Query query = em.createQuery("DELETE FROM EmlReadConfirmMail m"
				+ " WHERE m.confirmMailId IN :confirmMailIdList");

		query.setParameter("confirmMailIdList", confirmMailIdList);
		query.executeUpdate();
	}

	/**
	 * 수신확인 상세 정보 입력
	 * @param confirm 수신확인 상세 domain
	 */
	public void persist(EmlReadConfirm confirm) {
		em.persist(confirm);
	}

	/**
	 * 수신 확인 상세 정보 모두 삭제
	 * @param userId 이메일 아이디
	 */
	public void deleteAllReadConfirmDetail(String userId) {
		Query query = em.createQuery("DELETE FROM EmlReadConfirm c WHERE c.emlReadConfirmMail.confirmMailId IN"
				+ " (SELECT m.confirmMailId FROM EmlReadConfirmMail m WHERE m.emlUser.userId = :userId)");

		query.setParameter("userId", userId);
		query.executeUpdate();
	}

	/**
	 * 수신 확인 기본 정보 모두 삭제
	 * @param userId 이메일 아이디
	 */
	public void deleteAllReadConfirm(String userId) {
		Query query = em.createQuery("DELETE FROM EmlReadConfirmMail m WHERE m.emlUser.userId = :userId");

		query.setParameter("userId", userId);
		query.executeUpdate();
	}
}
