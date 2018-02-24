package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;

public abstract class AbstractEntity implements IEntity {

    private String id;

    public AbstractEntity() {
    }

    public AbstractEntity(Entity entity) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
