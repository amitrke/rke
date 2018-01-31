package org.roorkee.rkerestapi.dao;

import com.google.appengine.api.datastore.*;
import org.roorkee.rkerestapi.entity.IEntity;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

public abstract class AbstractDao<T extends IEntity> implements InitializingBean{

    private DatastoreService datastore;

    @Override
    public void afterPropertiesSet() throws Exception {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    public String create(T entity) {
        Key k = datastore.put(entity.toGoogleDatastoreEntity());
        return k.toString();
    }

    public T get(long id) throws RuntimeException{
        try {
            Entity gEntity = datastore.get(KeyFactory.createKey(getKind(), id));
            T entity = newEntity();
            entity.setGEntity(gEntity);
            return entity;
        }
        catch (EntityNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    abstract String getKind();

    abstract T newEntity();
}
