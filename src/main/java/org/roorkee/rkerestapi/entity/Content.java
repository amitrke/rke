package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class Content extends AbstractEntity{

    @GStoreAttr
    private String title;

    @GStoreAttr
    private String imageURL;

    @GStoreAttr
    private String description;

    @GStoreAttr(type = Text.class)
    private String fullText;

    @GStoreAttr
    private int priority;

    @Override
    public String getKeyKind() {
        return "Content";
    }

    @Override
    public Entity toGoogleDatastoreEntity() {
        Entity gDtaEntity = super.toGoogleDatastoreEntity();
        if (this.fullText != null) gDtaEntity.setProperty("fullText", new Text(this.fullText));
        return gDtaEntity;
    }

    @Override
    public void setGEntity(Entity entity) {
        this.setId(entity.getKey().getId());
        Map<String, Object> entityProperties = entity.getProperties();
        this.imageURL = (String) entityProperties.get("imageURL");
        this.description = (String) entityProperties.get("description");
        this.title = (String) entityProperties.get("title");
        this.priority = ((Long)entityProperties.get("priority")).intValue();
        if (entityProperties.get("fullText") instanceof Text){
            this.fullText = ((Text) entityProperties.get("fullText")).getValue();
        }
        else if (entityProperties.get("fullText") instanceof String){
            this.fullText = (String) entityProperties.get("fullText");
        }
    }
}
