package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.dao.UserDao;
import org.roorkee.rkerestapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserDao dao;

    @PostMapping(path = "/")
    public long create(@RequestBody User user){
        return dao.save(user);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public User get(@PathVariable Long id){
        return dao.get(id);
    }
}
