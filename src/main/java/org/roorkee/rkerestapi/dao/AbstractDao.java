package org.roorkee.rkerestapi.dao;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import org.roorkee.rkerestapi.entity.IEntity;

public abstract class AbstractDao<T extends IEntity>{

    public String create(T entity) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Key k = datastoreService.put(entity.toGoogleDatastoreEntity());
        return k.toString();
    }

}
