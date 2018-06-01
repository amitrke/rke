package org.roorkee.rkerestapi.entity;


import com.google.appengine.api.datastore.Entity;
import lombok.Data;
import org.roorkee.rkerestapi.util.RkeException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

@Data
public abstract class AbstractEntity<T> {

    @GStoreAttr
    private T id;

    @GStoreAttr
    private Date created;

    @GStoreAttr
    private String ownerUserId;

    @GStoreAttr
    private String status;

    public Entity toGoogleDatastoreEntity(){

        Entity gDtaEntity = null;
        if (this.id instanceof String){
            gDtaEntity = new Entity(getKeyKind(), (String) this.getId());
        }
        else if (this.id instanceof Long){
            gDtaEntity = new Entity(getKeyKind(), (Long) this.getId());
        }
        else {
            throw new RkeException(new RuntimeException("Unknown ID type"));
        }

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
                else{

                }
            }
        }

        return gDtaEntity;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public abstract void setGEntity(Entity entity);

    public abstract String getKeyKind();

}
