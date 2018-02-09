package org.roorkee.rkerestapi.dao;

import com.google.appengine.api.datastore.*;
import org.roorkee.rkerestapi.entity.IEntity;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
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

    public T get(String id) throws RuntimeException{
        try {
            //KeyFactory.createKey(getKind(), id)
            Entity gEntity = datastore.get(createKey(id));
            T entity = newEntity();
            entity.setGEntity(gEntity);
            return entity;
        }
        catch (EntityNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public List<T> list(){
        Query q = new Query(getKind());
        PreparedQuery pq = datastore.prepare(q);
        List<Entity> gEntityList = pq.asList(FetchOptions.Builder.withLimit(10));
        List<T> entityList = new ArrayList<T>();

        for (Entity gEntity: gEntityList){
            T entity = newEntity();
            entity.setGEntity(gEntity);
            entityList.add(entity);
        }
        return entityList;
    }

    abstract Key createKey(String id);

    abstract String getKind();

    abstract T newEntity();

}
