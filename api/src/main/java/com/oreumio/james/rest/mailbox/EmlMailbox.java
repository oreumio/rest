package com.oreumio.james.rest.mailbox;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Entity
@Table(name = "JAMES_MAILBOX")
public class EmlMailbox {

    @Id
    @Column(name = "MAILBOX_ID", nullable = false)
    private long id;
}
