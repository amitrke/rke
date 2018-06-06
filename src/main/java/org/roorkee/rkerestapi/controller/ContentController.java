package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.dao.ContentDao;
import org.roorkee.rkerestapi.entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@CrossOrigin
public class ContentController {

    @Autowired
    private ContentDao dao;

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public long create(@RequestBody Content content) {
        return dao.save(content);
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
