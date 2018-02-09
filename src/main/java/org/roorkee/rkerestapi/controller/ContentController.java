package org.roorkee.rkerestapi.controller;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import org.roorkee.rkerestapi.dao.ContentDao;
import org.roorkee.rkerestapi.entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    @Autowired
    private ContentDao dao;

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

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public String create(@RequestBody Content content) {
        return dao.create(content);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public Content get(@PathVariable String id) {
        return dao.get(id);
    }

    @GetMapping(path="/", produces = "application/json")
    public List<Content> list(){
        return dao.list();
    }
}
