package org.roorkee.rkerestapi.entity;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

import lombok.Data;

@Data
public class Article extends AbstractEntity{

    @GStoreAttr
    @NotNull
    private String title;

    @GStoreAttr
    @NotNull
    private String imageURL;

    @GStoreAttr
    @NotNull
    private String description;

    @GStoreAttr(type = Text.class)
    @NotNull
    private String fullText;

    @GStoreAttr
    @NotNull
    private Long priority;

    @Override
    public Entity toGoogleDatastoreEntity() {
        Entity gDtaEntity = super.toGoogleDatastoreEntity();
        if (this.fullText != null) gDtaEntity.setProperty("fullText", new Text(this.fullText));
        return gDtaEntity;
    }

    @Override
    public void setGEntity(Entity entity) {
        super.setGEntity(entity);
        Map<String, Object> entityProperties = entity.getProperties();
        if (entityProperties.get("fullText") instanceof Text){
            this.fullText = ((Text) entityProperties.get("fullText")).getValue();
        }
        else if (entityProperties.get("fullText") instanceof String){
            this.fullText = (String) entityProperties.get("fullText");
        }
    }

	@Override
	public void mockObj() {
		super.mockObj();
		description = "Test Description";
		title = "Test Title";
		fullText = "Test Full Text";
		imageURL = "/abc/def/a.jpg";
        priority = 1L;
	}
}
