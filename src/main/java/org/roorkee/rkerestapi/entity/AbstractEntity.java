package org.roorkee.rkerestapi.entity;


import com.google.appengine.api.datastore.Entity;
import lombok.Data;

import java.util.Date;

@Data
public abstract class AbstractEntity<T> {

    private T id;

    private Date created;

    private User owner;

    private String status;

    public Entity toGoogleDatastoreEntity(){
        Entity gDtaEntity = null;
        if (this.id instanceof String){
            gDtaEntity = new Entity(getKeyKind(), (String) this.getId());
        }
        else if (this.id instanceof Long){
            gDtaEntity = new Entity(getKeyKind(), (Long) this.getId());
        }

        return gDtaEntity;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public abstract void setGEntity(Entity entity);

    public abstract String getKeyKind();

}
