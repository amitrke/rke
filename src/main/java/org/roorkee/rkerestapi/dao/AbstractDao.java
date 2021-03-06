package org.roorkee.rkerestapi.dao;

import com.google.appengine.api.datastore.*;
import org.roorkee.rkerestapi.entity.AbstractEntity;
import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractDao<T extends AbstractEntity> implements InitializingBean{

    private DatastoreService datastore;

    @Override
    public void afterPropertiesSet() throws Exception {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    public void delete(long id){
        datastore.delete(createKey(id));
    }

    public long save(T entity) {
        if (entity.getCreated() == null){
            entity.setCreated(new Date());
        }
        if (entity.getStatus() == null){
            entity.setStatus("pending-review");
        }
        Key k = datastore.put(entity.toGoogleDatastoreEntity());
        return k.getId();
    }

    public T get(Long id) {
        try {
            Entity gEntity = datastore.get(createKey(id));
            T entity = newEntity();
            entity.setGEntity(gEntity);
            return entity;
        }
        catch (EntityNotFoundException e){
            throw new RkeException(e);
        }
    }

    public List<T> list(){
        Query q = new Query(getKind());
        PreparedQuery pq = datastore.prepare(q);
        List<Entity> gEntityList = pq.asList(FetchOptions.Builder.withLimit(10));
        return dsEntityToAppEntity(gEntityList);
    }

    private List<T> dsEntityToAppEntity(List<Entity> gEntityList){
        List<T> entityList = new ArrayList<T>();

        for (Entity gEntity: gEntityList){
            T entity = newEntity();
            entity.setGEntity(gEntity);
            entityList.add(entity);
        }
        return entityList;
    }

    public List<T> search(T entity){
        Query q = new Query(getKind());
        for(Query.Filter filter: entity.getDatastoreFilter()){
            q.setFilter(filter);
        }
        PreparedQuery pq = datastore.prepare(q);
        List<Entity> gEntityList = pq.asList(FetchOptions.Builder.withLimit(10));
        return dsEntityToAppEntity(gEntityList);
    }

    private Key createKey(Long id) {
        return KeyFactory.createKey(getKind(), id);
    }

    abstract String getKind();

    abstract T newEntity();
}
