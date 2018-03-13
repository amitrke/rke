package org.roorkee.rkerestapi.entity;


import java.util.Date;

public abstract class AbstractEntity implements IEntity {

    private Date created;

    private User owner;

    private String status;

    public AbstractEntity(Date created, User owner, String status) {
        this.created = created;
        this.owner = owner;
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public User getOwner() {
        return owner;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "created=" + created +
                ", owner=" + owner +
                ", status='" + status + '\'' +
                '}';
    }
}
