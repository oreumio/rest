package com.oreumio.james.rest.send;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class EmlMailKeyVo {

    private String mailboxName;

    private long uid;

    public EmlMailKeyVo() {
    }

    public EmlMailKeyVo(String mailboxName, long uid) {
        this.mailboxName = mailboxName;
        this.uid = uid;
    }

    public String getMailboxName() {
        return mailboxName;
    }

    public void setMailboxName(String mailboxName) {
        this.mailboxName = mailboxName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "EmlMailKeyVo{" +
                "mailboxName='" + mailboxName + '\'' +
                ", uid=" + uid +
                '}';
    }
}
