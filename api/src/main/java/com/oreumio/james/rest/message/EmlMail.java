package com.oreumio.james.rest.message;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@IdClass(EmlMail.MailboxIdUidKey.class)
@Table(name = "JAMES_MAIL")
public class EmlMail {

    public static class MailboxIdUidKey implements Serializable {

        private static final long serialVersionUID = 7847632032426660997L;

        public MailboxIdUidKey() {}

        public MailboxIdUidKey(long mailboxId, long uid) {
            this.mailboxId = mailboxId;
            this.uid = uid;
        }

        /** The value for the mailbox field */
        public long mailboxId;

        /** The value for the uid field */
        public long uid;

        @Override
        public int hashCode() {
            final int PRIME = 31;
            int result = 1;
            result = PRIME * result + (int) (mailboxId ^ (mailboxId >>> 32));
            result = PRIME * result + (int) (uid ^ (uid >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final MailboxIdUidKey other = (MailboxIdUidKey) obj;
            if (mailboxId != other.mailboxId)
                return false;
            if (uid != other.uid)
                return false;
            return true;
        }

    }

    @Id
    @Column(name = "MAIL_UID", nullable = false)
    private long uid;

    @Id
    @Column(name = "MAILBOX_ID", nullable = false)
    private long mailboxId;

    @Column(name = "MAIL_IS_ANSWERED", nullable = false)
    private boolean answered;

    @Column(name = "MAIL_IS_DELETED", nullable = false)
    private boolean deleted;

    @Column(name = "MAIL_IS_DRAFT", nullable = false)
    private boolean draft;

    @Column(name = "MAIL_IS_FLAGGED", nullable = false)
    private boolean flagged;

    @Column(name = "MAIL_IS_RECENT", nullable = false)
    private boolean recent;

    @Column(name = "MAIL_IS_SEEN", nullable = false)
    private boolean seen;

    @Column(name = "MAIL_IS_APPROVED", nullable = false)
    private boolean approved;

    @Column(name = "MAIL_IS_SECURED", nullable = false)
    private boolean secured;

    @Column(name = "MAIL_IS_ATTACHED", nullable = false)
    private boolean attached;

    @Column(name = "MAIL_IS_IMPORTANT", nullable = false)
    private boolean important;

    @Column(name = "MAIL_IS_STARRED", nullable = false)
    private boolean starred;

    @Column(name = "MAIL_IS_READ_RES_YN", nullable = false)
    private boolean readReponded;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MAIL_SEND_DT", nullable = false)
    private Date date;

    @Column(name = "CHAR_SET", nullable = false, length = 30)
    private String charset;

    @Column(name = "DISP_MAIL_FROM", nullable = false, length = 30)
    private String displayFrom;

    @Column(name = "DISP_MAIL_TO", nullable = false, length = 30)
    private String displayTo;

    @Column(name = "MAIL_TO_CNT", nullable = false)
    private int toCount;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "MSG_ID", nullable = false)
    private String messageId;

    @Column(name = "PROC_STATUS", nullable = false)
    private String processState;

    @Column(name = "SUMRY_CN", nullable = false, length = 1000)
    private String summary;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MAIL_DATE", nullable = false)
    private Date internalDate;

    @Column(name = "MAIL_MIME_TYPE", nullable = false, length = 30)
    private String mimeType;

    @Column(name = "MAIL_MIME_SUBTYPE", nullable = false, length = 30)
    private String mimeSubtype;

    @Column(name = "MAIL_BYTES", length = 1048576000, nullable = false)
    @Lob private byte[] body;

    @Column(name = "HEADER_BYTES", length = 1048576000, nullable = false)
    @Lob private byte[] header;

    public EmlMail() {
    }

    public EmlMail(EmlMailVo emlMailVo) {
        uid = emlMailVo.getUid();
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

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "EmlMail{" +
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
