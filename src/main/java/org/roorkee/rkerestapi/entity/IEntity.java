package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;

public interface IEntity {

    public Entity toGoogleDatastoreEntity();

}
