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
}
