package com.oreumio.james.rest.message;

import java.util.Date;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlMailVo {

    private long id;

    /**
     * 제목
     */
    private String subject;

    /**
     * 요약 내용
     */
    private String sumryCn;

    /**
     * 표시용 메일 받는 사람
     */
    private String dispMailTo;

    /**
     * 표시용 메일 보낸 사람
     */
    private String dispMailFrom;

    /**
     * 메일 받는 사람 수(총 받는 사람수-1)
     */
    private short mailToCnt;

    /**
     * 메일 사이즈
     */
    private long mailSize;

    /**
     * 읽음 여부
     */
    private String readYn;

    /**
     * 삭제 여부
     */
    private String delYn;

    /**
     * 별표 여부
     */
    private String starYn;

    /**
     * 파일 첨부 여부
     */
    private String fileAttYn;

    /**
     * 중요 여부
     */
    private String importYn;

    /**
     * 보안 메일 여부
     */
    private String secMailYn;

    /**
     * 승인 메일 여부
     */
    private String apprMailYn;

    /**
     * 메일 보낸 일시
     */
    private Date mailSendDt;

    /**
     * 메일 받은 일시
     */
    private Date mailRecvDt;

    /**
     * 메세지 아이디
     */
    private String msgId;

    /**
     * 문자셋
     */
    private String charSet;

    /**
     * 메일 UID
     */
    private long uid;

    /**
     * 처리 상태(R : 답장 메일 여부,F : 전달 메일 여부,A : 부재중 응답 여부)
     */
    private String procState;

    /**
     * 메일함 이름
     */
    private String mailboxName;

    public EmlMailVo() {
    }

    public EmlMailVo(EmlMail emlMail) {
        id = emlMail.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
