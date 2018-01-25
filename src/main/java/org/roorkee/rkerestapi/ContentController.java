package org.roorkee.rkerestapi;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }

    @PostMapping("/testCreate")
    public String testCreate() {
        Entity testEntity = new Entity("TestEntity");
        testEntity.setProperty("name", "Test Name");
        testEntity.setProperty("value", "Test Value");
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Key k = datastoreService.put(testEntity);
        return k.toString();
    }
}
