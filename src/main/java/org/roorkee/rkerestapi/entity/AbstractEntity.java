package org.roorkee.rkerestapi.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.roorkee.rkerestapi.util.RkeException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

@Data
public abstract class AbstractEntity {

    @GStoreAttr
    protected Long id;

    @GStoreAttr
    private Date created;

    @GStoreAttr
    private Long userId;

    @GStoreAttr
    private String status;

    @JsonIgnore
    public List<Query.Filter> getDatastoreFilter() {
        List<Query.Filter> filter = new ArrayList<Query.Filter>();

        Field[] fields = ArrayUtils.addAll(this.getClass().getSuperclass().getDeclaredFields(), this.getClass().getDeclaredFields());

        for (Field field : fields) {
            if (field.isAnnotationPresent(GStoreAttr.class)) {
                Annotation annotation = field.getAnnotation(GStoreAttr.class);
                GStoreAttr gStoreAttr = (GStoreAttr) annotation;
                if (gStoreAttr.type().equals(Object.class)) {
                    try {
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), this.getClass());
                        Object variableValue = propertyDescriptor.getReadMethod().invoke(this);
                        if (variableValue != null) {
                            filter.add(new Query.FilterPredicate(field.getName(), Query.FilterOperator.EQUAL, variableValue));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RkeException(e);
                    } catch (IntrospectionException e) {
                        throw new RkeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RkeException(e);
                    }
                }
            }
        }
        return filter;
    }

    public Entity toGoogleDatastoreEntity(){

        Entity gDtaEntity = null;
        if (this.getId() != null){
            gDtaEntity = new Entity(getKeyKind(), getId());
        }
        else{
            gDtaEntity = new Entity(getKeyKind());
        }

        if (this.created != null) gDtaEntity.setProperty("created", this.created);
        if (this.userId != null) gDtaEntity.setProperty("userId", this.userId);
        if (this.status != null) gDtaEntity.setProperty("status", this.status);

        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field: fields){
            if (field.isAnnotationPresent(GStoreAttr.class)){
                Annotation annotation = field.getAnnotation(GStoreAttr.class);
                GStoreAttr gStoreAttr = (GStoreAttr) annotation;
                if (gStoreAttr.type().equals(Object.class)){
                    try {
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), this.getClass());
                        Object variableValue = propertyDescriptor.getReadMethod().invoke(this);
                        if (variableValue != null) gDtaEntity.setProperty(field.getName(), variableValue);
                    }
                    catch (IllegalAccessException e){
                        throw new RkeException(e);
                    }
                    catch (IntrospectionException e){
                        throw new RkeException(e);
                    }
                    catch (InvocationTargetException e){
                        throw new RkeException(e);
                    }
                }
            }
        }
        return gDtaEntity;
    }

    public void setGEntity(Entity entity){
        this.setId(entity.getKey().getId());
        Map<String, Object> entityProperties = entity.getProperties();

        if (entityProperties.containsKey("created")) this.setCreated((Date)entityProperties.get("created"));
        if (entityProperties.containsKey("userId")) this.setUserId((Long)entityProperties.get("userId"));
        if (entityProperties.containsKey("status")) this.setStatus((String)entityProperties.get("status"));

        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field: fields){
            if (field.isAnnotationPresent(GStoreAttr.class) && entityProperties.containsKey(field.getName())){
                Annotation annotation = field.getAnnotation(GStoreAttr.class);
                GStoreAttr gStoreAttr = (GStoreAttr) annotation;
                if (gStoreAttr.type().equals(Object.class)){
                    try {
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), this.getClass());
                        Method method = propertyDescriptor.getWriteMethod();
                        Class<?> cls = method.getParameterTypes()[0];
                        method.invoke(this, cls.cast(entityProperties.get(field.getName())));
                    }
                    catch (IntrospectionException e) {
                        throw new RkeException(e);
                    }
                    catch (IllegalAccessException e){
                        throw new RkeException(e);
                    }
                    catch (InvocationTargetException e){
                        throw new RkeException(e);
                    }
                }
            }
        }
    }

    public String getKeyKind(){
        return this.getClass().getSimpleName();
    }
    
    public void mockObj() {
    	status = "Active";
    	userId = 1L;
    	created = new Date();
    }
}
