package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import java.util.Map;

public class Content implements IEntity{

    private String id;
    private String title;
    private String imageURL;
    private String description;
    private String fullText;
    private String userId;
    private int priority;

    public Content() {
    }

    public Content(String id, String imageURL, String description, String fullText) {
        this.id = id;
        this.imageURL = imageURL;
        this.description = description;
        this.fullText = fullText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getKeyKind() {
        return "Content";
    }

    @Override
    public Entity toGoogleDatastoreEntity() {
        Entity gDtaEntity = new Entity(getKeyKind(), this.id);
        gDtaEntity.setProperty("imageURL", this.imageURL);
        gDtaEntity.setProperty("description", this.description);
        gDtaEntity.setProperty("fullText", new Text(this.fullText));
        gDtaEntity.setProperty("title", this.title);
        return gDtaEntity;
    }

    @Override
    public void setGEntity(Entity entity) {
        this.id = entity.getKey().getName();
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

    @Override
    public String toString() {
        return "Content{" +
                "id='" + id + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", description='" + description + '\'' +
                ", fullText='" + fullText + '\'' +
                '}';
    }
}
