package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class Content extends AbstractEntity<String>{

    private String title;
    private String imageURL;
    private String description;
    private String fullText;
    private int priority;

    @Override
    public String getKeyKind() {
        return "Content";
    }

    @Override
    public Entity toGoogleDatastoreEntity() {
        Entity gDtaEntity = super.toGoogleDatastoreEntity();
        gDtaEntity.setProperty("imageURL", this.imageURL);
        gDtaEntity.setProperty("description", this.description);
        gDtaEntity.setProperty("fullText", new Text(this.fullText));
        gDtaEntity.setProperty("title", this.title);
        return gDtaEntity;
    }

    @Override
    public void setGEntity(Entity entity) {
        this.setId(entity.getKey().getName());
        Map<String, Object> entityProperties = entity.getProperties();
        this.imageURL = (String) entityProperties.get("imageURL");
        this.description = (String) entityProperties.get("description");
        this.title = (String) entityProperties.get("title");
        if (entityProperties.get("fullText") instanceof Text){
            this.fullText = ((Text) entityProperties.get("fullText")).getValue();
        }
        else if (entityProperties.get("fullText") instanceof String){
            this.fullText = (String) entityProperties.get("fullText");
        }
    }
}
