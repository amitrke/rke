package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;
import lombok.Data;

import java.util.Map;

@Data
public class User extends AbstractEntity<String> {

    private String email;
    private String name;
    private String imageURL;
    private String indicator;

    @Override
    public Entity toGoogleDatastoreEntity() {
        Entity gDtaEntity = new Entity(getKeyKind(), this.getId());
        gDtaEntity.setProperty("email", this.email);
        gDtaEntity.setProperty("name", this.name);
        gDtaEntity.setProperty("indicator", this.indicator);
        gDtaEntity.setProperty("imageURL", this.imageURL);
        return gDtaEntity;
    }

    @Override
    public void setGEntity(Entity entity) {
        this.setId(entity.getKey().getName());
        Map<String, Object> entityProperties = entity.getProperties();
        this.imageURL = (String) entityProperties.get("imageURL");
        this.email = (String) entityProperties.get("email");
        this.name = (String) entityProperties.get("name");
        this.indicator = (String) entityProperties.get("indicator");
    }

    @Override
    public String getKeyKind() {
        return "User";
    }
}
