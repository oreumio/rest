package com.oreumio.james.rest.util;

/**
 * <pre>
 * 메일 관련 상수
 * </pre>
 * 
 * @author  : doyoung Choi
 * @version : 1.0.0
 */
public final class EmlCommonConstants {

	/** 라벨 계층 구조:LABEL_LOC_NAME 컬럼(│라벨명1│라벨명2│라벨명3)의 구분자 : 사용자 화면에 보여줄 때는 / 문자로 치환 */
	public static final String LABEL_DELIMITER = "│";

	/** 받은 메일함 물리 경로 */
	public static final String RECV_BOX = "INBOX";

	/** 보낸 메일함 물리 경로 */
	public static final String SENT_BOX = "Sent";

	/** 삭제함 물리 경로 */
	public static final String TRASH_BOX = "Trash";

	/** 임시 보관함 물리 경로 */
	public static final String DRAFT_BOX = "Drafts";

	/** 예약 메일함 물리 경로 */
	public static final String RESERVED_BOX = "Reserved";

	/** 스팸 메일함 물리 경로 */
	public static final String SPAM_BOX = "Spam";

	/**
	 * 메뉴 코드 목록 mailboxType 
	 * 안읽은 메일, 수신확인, 그룹일정초대, 발송승인-보낸메일, 발송승인-승인할 메일,
	 * 전체 받은 메일, 받은, 보낸, 삭제, 임시, 예약, 스팸 메일함
	 */
	public static final String[] MENU_CODE_LIST = {"unread", "readConfirm", "invite", "apprSent", "apprWill",
		"all", RECV_BOX, SENT_BOX, TRASH_BOX, DRAFT_BOX, RESERVED_BOX, SPAM_BOX};

	/**
	 * 메일함 코드 목록 boxCode 
	 */
	public static final String[] BOX_CODE_LIST = {"recv", "label", "personal", "all", "unread", "spam", "del",
		"sent", "temp", "reserv", "read", "apprWill", "apprSent"};

	/**
	 * 부서,회사, 직위등등 대표메일 주소 길이 
	 */
	public static final int REPRSNT_SIZE = 20;

	/**
	 * 목록 표시 갯수
	 */
	public static final int[] MAIL_LIST_SIZE = {15, 30, 50, 100};

	/**
	 * 보낸 날짜로 정렬되는 메일함 코드
	 */
	public static final String[] DISPLAY_SENT_DT_BOXCODE = {"sent", "temp", "reserv"};

	public static final String WEB_BUG = "<!--_webbug_:S-->"
			+ "<div style=\"background:url('%s/data/emlReadConfirmMail/recvConfirm.do?confirmId=##**CONFIRM_ID**##')\">"
			+ "</div><!--_webbug_:E-->";

	/**
	 * 수신확인 마스터 아이디가 저장될 메일 커스텀 헤더
	 */
	public static final String RECV_MASTER_CUSTOM_HEADER = "X-Disposition-Notification-To";

	/**
	 * 최초 수신자 이메일 아이디가 저장될 메일 커스텀 헤더
	 */
	public static final String RECV_EMAIL_CUSTOM_HEADER = "X-Delivered-To";

	/**
	 * 대용량 메일 테이블 PK(예약 메일 발송시 사용)
	 */
	public static final String MASSIVE_MAIL_CUSTOM_HEADER = "X-MassiveMailId";

	/**
	 * Constructor.
	 */
	private EmlCommonConstants() { }
}
