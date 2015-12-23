package com.oreumio.james.rest.message;

import java.util.Date;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlMailVo {

    private long uid;

    private long mailboxId;

    private boolean answered;

    private boolean deleted;

    private boolean draft;

    private boolean flagged;

    private boolean recent;

    private boolean seen;

    private boolean approved;

    private boolean secured;

    private boolean attached;

    private boolean important;

    private boolean starred;

    private boolean readReponded;

    private Date date;

    private String charset;

    private String displayFrom;

    private String displayTo;

    private int toCount;

    private String subject;

    private String messageId;

    private String processState;

    private String summary;

    private Date internalDate;

    private String mimeType;

    private String mimeSubtype;

    public EmlMailVo() {
    }

    public EmlMailVo(EmlMail emlMail) {
        uid = emlMail.getUid();

        mailboxId = emlMail.getMailboxId();

        answered = emlMail.isAnswered();

        deleted = emlMail.isDeleted();

        draft = emlMail.isDraft();

        flagged = emlMail.isFlagged();

        recent = emlMail.isRecent();

        seen = emlMail.isSeen();

        approved = emlMail.isApproved();

        secured = emlMail.isSecured();

        attached = emlMail.isAttached();

        important = emlMail.isImportant();

        starred = emlMail.isStarred();

        readReponded = emlMail.isReadReponded();

        date = emlMail.getDate();

        charset = emlMail.getCharset();

        displayFrom = emlMail.getDisplayFrom();

        displayTo = emlMail.getDisplayTo();

        toCount = emlMail.getToCount();

        subject = emlMail.getSubject();

        messageId = emlMail.getMessageId();

        processState = emlMail.getProcessState();

        summary = emlMail.getSummary();

        internalDate = emlMail.getInternalDate();

        mimeType = emlMail.getMimeType();

        mimeSubtype = emlMail.getMimeSubtype();
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getMailboxId() {
        return mailboxId;
    }

    public void setMailboxId(long mailboxId) {
        this.mailboxId = mailboxId;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isRecent() {
        return recent;
    }

    public void setRecent(boolean recent) {
        this.recent = recent;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isSecured() {
        return secured;
    }

    public void setSecured(boolean secured) {
        this.secured = secured;
    }

    public boolean isAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public boolean isReadReponded() {
        return readReponded;
    }

    public void setReadReponded(boolean readReponded) {
        this.readReponded = readReponded;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getDisplayFrom() {
        return displayFrom;
    }

    public void setDisplayFrom(String displayFrom) {
        this.displayFrom = displayFrom;
    }

    public String getDisplayTo() {
        return displayTo;
    }

    public void setDisplayTo(String displayTo) {
        this.displayTo = displayTo;
    }

    public int getToCount() {
        return toCount;
    }

    public void setToCount(int toCount) {
        this.toCount = toCount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getInternalDate() {
        return internalDate;
    }

    public void setInternalDate(Date internalDate) {
        this.internalDate = internalDate;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeSubtype() {
        return mimeSubtype;
    }

    public void setMimeSubtype(String mimeSubtype) {
        this.mimeSubtype = mimeSubtype;
    }

    @Override
    public String toString() {
        return "EmlMailVo{" +
                "uid=" + uid +
                ", mailboxId=" + mailboxId +
                ", answered=" + answered +
                ", deleted=" + deleted +
                ", draft=" + draft +
                ", flagged=" + flagged +
                ", recent=" + recent +
                ", seen=" + seen +
                ", approved=" + approved +
                ", secured=" + secured +
                ", attached=" + attached +
                ", important=" + important +
                ", starred=" + starred +
                ", readReponded=" + readReponded +
                ", date=" + date +
                ", charset='" + charset + '\'' +
                ", displayFrom='" + displayFrom + '\'' +
                ", displayTo='" + displayTo + '\'' +
                ", toCount=" + toCount +
                ", subject='" + subject + '\'' +
                ", messageId='" + messageId + '\'' +
                ", processState='" + processState + '\'' +
                ", summary='" + summary + '\'' +
                ", internalDate=" + internalDate +
                ", mimeType='" + mimeType + '\'' +
                ", mimeSubtype='" + mimeSubtype + '\'' +
                '}';
    }
}
