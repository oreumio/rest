package com.oreumio.james.rest.mailbox;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlMailboxVo {

    /**
     * 메일함 경로
     */
    private String mailboxName;

    private String displayName;

    /**
     * 메일 사용 용량
     */
    private long size;

    /**
     * 메일 갯수
     */
    private long count;

    /**
     * 안읽은 메일 갯수
     */
    private long unseenCount;

    /**
     * 메일함 순서
     */
    private int mailboxOrder;

    public EmlMailboxVo() {
    }

    public EmlMailboxVo(EmlMailbox emlMailbox) {
        mailboxName = emlMailbox.getName();
        size = emlMailbox.getUsed();
        count = emlMailbox.getCount();
        unseenCount = emlMailbox.getUnread();
        mailboxOrder = emlMailbox.getOrder();
    }

    public String getMailboxName() {
        return mailboxName;
    }

    public void setMailboxName(String mailboxName) {
        this.mailboxName = mailboxName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getUnseenCount() {
        return unseenCount;
    }

    public void setUnseenCount(long unseenCount) {
        this.unseenCount = unseenCount;
    }

    public int getMailboxOrder() {
        return mailboxOrder;
    }

    public void setMailboxOrder(int mailboxOrder) {
        this.mailboxOrder = mailboxOrder;
    }

    @Override
    public String toString() {
        return "EmlMailboxVo{" +
                "mailboxName='" + mailboxName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", size=" + size +
                ", count=" + count +
                ", unseenCount=" + unseenCount +
                ", mailboxOrder=" + mailboxOrder +
                '}';
    }
}
