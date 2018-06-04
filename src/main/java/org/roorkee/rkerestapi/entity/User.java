package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;
import lombok.Data;

import java.util.Map;

@Data
public class User extends AbstractEntity {
    
    @GStoreAttr
    private String email;
    
    @GStoreAttr
    private String name;
    
    @GStoreAttr
    private String imageURL;
    
    @GStoreAttr
    private String indicator;

    @Override
    public void setGEntity(Entity entity) {
        this.setId(entity.getKey().getId());
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
