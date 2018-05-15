package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;

import java.util.Date;

public class Image extends AbstractEntity {

    private Long id;

    private String fileName;

    public Image(Date created, User owner, String status, Long id, String fileName) {
        super(created, owner, status);
        this.id = id;
        this.fileName = fileName;
    }

    public Image(String fileName) {
        super(new Date(), null, null);
        this.fileName = fileName;
    }

    @Override
    public Entity toGoogleDatastoreEntity() {
        return null;
    }

    @Override
    public void setGEntity(Entity entity) {

    }

    @Override
    public String getKeyKind() {
        return null;
    }
}
