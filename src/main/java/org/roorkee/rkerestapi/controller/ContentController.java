package org.roorkee.rkerestapi.controller;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import org.roorkee.rkerestapi.dao.ContentDao;
import org.roorkee.rkerestapi.entity.Content;
import org.roorkee.rkerestapi.entity.HelloWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@CrossOrigin
public class ContentController {

    @Autowired
    private ContentDao dao;

    @GetMapping("/hello")
    public HelloWorld hello() {
        HelloWorld helloWorld = new HelloWorld();
        return helloWorld;
    }

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public long create(@RequestBody Content content) {
        return dao.create(content);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public Content get(@PathVariable Long id) {
        return dao.get(id);
    }

    @GetMapping(path="/", produces = "application/json")
    public List<Content> list(){
        return dao.list();
    }
}
