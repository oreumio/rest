package com.oreumio.james.rest.send;

import com.oreumio.james.rest.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * <pre>
 * 수신 확인 메일 DB 작업
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Repository
public class EmlReadConfrimMailDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlReadConfrimMailDao.class);

	@PersistenceContext(unitName = "rest")
	private EntityManager em;

	/**
	 * @param em EntityManager
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * 수신확인 목록 총 갯수을 가져온다.
	 * @return 수신확인 목록 총 갯수
	 */
	public long selectConfirmMailCount(String userId, String startDate, String endDate, String key, String value) {
		String queryStr = "SELECT COUNT(c.confirmMailId) FROM EmlReadConfirmMail c WHERE c.emlUser.userId = :userId";
		if (!StringUtils.isEmpty(value)) { //검색어가 있다면
			if ("mailTo".equals(key)) { //받는 사람
				queryStr += " AND EXISTS (SELECT m.confirmId FROM EmlReadConfirm m "
						+ "WHERE m.emlReadConfirmMail = c AND m.mailTo LIKE :keyword) ";
			} else if ("subject".equals(key)) { //유저 아이디
				queryStr += " AND c.subject LIKE :keyword ";
			}
		}

		//기간 선택으로 한 경우(시작일)
		if (!StringUtils.isEmpty(startDate)) {
			queryStr += " AND c.mailSendDt >= :startDt";
		}

		//기간 선택으로 한 경우(종료일)
		if (!StringUtils.isEmpty(endDate)) {
			queryStr += " AND c.mailSendDt <= :endDt";
		}

		Query queryTotal = em.createQuery(queryStr);
		queryTotal.setParameter("userId", userId); //메일 유저 아이디
		if (StringUtils.contains(queryStr, ":keyword")) {
			queryTotal.setParameter("keyword", "%" + value + "%"); //검색어
		}
		if (StringUtils.contains(queryStr, ":startDt")) {
			queryTotal.setParameter("startDt", DateUtil.stringToDate(startDate)); //시작일
		}
		if (StringUtils.contains(queryStr, ":endDt")) {
			queryTotal.setParameter("endDt", DateUtil.stringToDate(endDate)); //종료일
		}
		return (Long) queryTotal.getSingleResult();
	}

	/**
	 * 수신확인 목록을 가져온다.
	 * @return 수신확인 목록
	 */
	public List<EmlReadConfirmMailVo> selectConfirmMailList(String userId, String startDate, String endDate, String key, String value, String orderCol, String orderType, int pageNo, int pageSize) {
		String queryStr = "SELECT NEW " + EmlReadConfirmMailVo.class.getName() + "(c.confirmMailId, c.subject,"
				+ " c.dispMailTo, c.readCnt, c.rcverCnt, c.msgId, c.mailSendDt, c.mailToCnt)"
				+ " FROM EmlReadConfirmMail c WHERE c.emlUser.userId = :userId";
		if (!StringUtils.isEmpty(value)) { //검색어가 있다면
			if ("mailTo".equals(key)) { //받는 사람
				queryStr += " AND EXISTS (SELECT m.confirmId FROM EmlReadConfirm m "
						+ "WHERE m.emlReadConfirmMail = c AND m.mailTo LIKE :keyword) ";
			} else if ("subject".equals(key)) { //유저 아이디
				queryStr += " AND c.subject LIKE :keyword ";
			}
		}
		//기간 선택으로 한 경우(시작일)
		if (!StringUtils.isEmpty(startDate)) {
			queryStr += " AND c.mailSendDt >= :startDt";
		}

		//기간 선택으로 한 경우(종료일)
		if (!StringUtils.isEmpty(endDate)) {
			queryStr += " AND c.mailSendDt <= :endDt";
		}

		String orderByStr = " ORDER BY c." + StringUtils.defaultIfEmpty(orderCol, "mailSendDt") + " " + orderType;

		TypedQuery<EmlReadConfirmMailVo> query = em.createQuery(queryStr + orderByStr,
				EmlReadConfirmMailVo.class);
		query.setParameter("userId", userId); //메일 유저 아이디
		if (StringUtils.contains(queryStr, ":keyword")) {
			query.setParameter("keyword", "%" + value + "%"); //검색어
		}
		if (StringUtils.contains(queryStr, ":startDt")) {
			query.setParameter("startDt", DateUtil.stringToDate(startDate)); //시작일
		}
		if (StringUtils.contains(queryStr, ":endDt")) {
			query.setParameter("endDt", DateUtil.stringToDate(endDate)); //종료일
		}
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	/**
	 * 수신확인 기본 정보를 가져온다.
	 * @param confirmMailId 수신 확인 메일 아이디
	 * @return 수신 확인 기본 정보
	 */
	public EmlReadConfirmMailVo selectReadConfirmMail(String confirmMailId) {
		TypedQuery<EmlReadConfirmMailVo> query = em.createQuery("SELECT"
				+ " NEW " + EmlReadConfirmMailVo.class.getName() + "(c.confirmMailId, c.subject, c.dispMailTo,"
				+ " c.readCnt,c.rcverCnt, c.msgId, c.mailSendDt, c.mailToCnt) FROM EmlReadConfirmMail c"
				+ " WHERE c.confirmMailId = :confirmMailId", EmlReadConfirmMailVo.class);
			query.setParameter("confirmMailId", confirmMailId); //수신확인 메일 아이디
			try {
				return query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
	}

	/**
	 * 수신 확인 부모 정보 입력
	 * @param readConfirmMail 수신확인 부모
	 */
	public void persistSimple(EmlReadConfirmMail readConfirmMail) {
		em.persist(readConfirmMail);
	}

	/**
	 * 메일 메세지 아이디로 수신확인 부모 테이블 정보를 가져온다.
	 * @param msgId 메일 메세지 아아디
	 * @return 수신확인 부모 테이블 정보
	 */
	public EmlReadConfirmMailVo selectConfirmMailByMsgId(String msgId) {
		TypedQuery<EmlReadConfirmMailVo> query = em.createQuery("SELECT DISTINCT"
			+ " NEW " + EmlReadConfirmMailVo.class.getName() + "(c.confirmMailId,"
			+ " c.subject, c.dispMailTo, c.readCnt,"
			+ " c.rcverCnt, c.msgId, c.mailSendDt, c.mailToCnt) FROM EmlReadConfirmMail c"
			+ " WHERE c.msgId = :msgId", EmlReadConfirmMailVo.class);
		query.setParameter("msgId", msgId); //보낸 메일함에 저장된 메세지 아이디
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
