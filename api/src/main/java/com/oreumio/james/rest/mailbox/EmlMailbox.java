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

    @Column(name = "USER_ID", nullable = false, length = 30)
    private String userId;

    @Column(name = "MAILBOX_NAME", nullable = false)
    private String name;

    @Column(name = "MAILBOX_TYPE", nullable = false, length = 1)
    private String type;

    @Column(name = "MAILBOX_COUNT", nullable = false)
    private long count;

    @Column(name = "MAILBOX_UNREAD", nullable = false)
    private long unread;

    @Column(name = "MAILBOX_USED", nullable = false)
    private long used;

    @Column(name = "MAILBOX_ORDER", nullable = false)
    private int order;

    public EmlMailbox() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getUnread() {
        return unread;
    }

    public void setUnread(long unread) {
        this.unread = unread;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "EmlMailbox{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", count=" + count +
                ", unread=" + unread +
                ", used=" + used +
                ", order=" + order +
                '}';
    }
}
