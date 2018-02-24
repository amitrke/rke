package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;

import java.util.Map;

public class User implements IEntity {

    private String id;
    private String email;
    private String name;
    private String imageURL;
    private String indicator;

    public User() {
    }

    public User(String id, String email, String name, String imageURL, String indicator) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.imageURL = imageURL;
        this.indicator = indicator;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getIndicator() {
        return indicator;
    }

    @Override
    public Entity toGoogleDatastoreEntity() {
        Entity gDtaEntity = new Entity(getKeyKind(), this.id);
        gDtaEntity.setProperty("email", this.email);
        gDtaEntity.setProperty("name", this.name);
        gDtaEntity.setProperty("indicator", this.indicator);
        gDtaEntity.setProperty("imageURL", this.imageURL);
        return gDtaEntity;
    }

    @Override
    public void setGEntity(Entity entity) {
        this.id = entity.getKey().getName();
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
