package com.oreumio.james.rest.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "JAMES_MESSAGE")
public class EmlMail {

    @Id
    @Column(name = "MAIL_ID", nullable = false)
    private long id;

    @Column(name = "MAILBOX_ID", nullable = false)
    private long mailboxId;

    public EmlMail() {
    }

    public EmlMail(EmlMailVo emlMailVo) {
        id = emlMailVo.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMailboxId() {
        return mailboxId;
    }

    public void setMailboxId(long mailboxId) {
        this.mailboxId = mailboxId;
    }
}
