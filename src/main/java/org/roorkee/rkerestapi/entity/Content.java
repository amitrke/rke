package org.roorkee.rkerestapi.entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

public class Content implements IEntity{

    private String id;
    private String imageURL;
    private String description;
    private String fullText;

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

    @Override
    public Entity toGoogleDatastoreEntity() {
        Entity gDtaEntity = new Entity("Content");
        gDtaEntity.setProperty("id", this.id);
        gDtaEntity.setProperty("imageURL", this.imageURL);
        gDtaEntity.setProperty("description", this.description);
        gDtaEntity.setProperty("fullText", new Text(this.fullText));
        return gDtaEntity;
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
