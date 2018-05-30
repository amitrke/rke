package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class Image extends AbstractEntity<Long> {

    private String fileName;

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
