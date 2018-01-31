package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public interface IEntity {

    public Entity toGoogleDatastoreEntity();

    public void setGEntity(Entity entity);

    public String getKeyKind();
}
