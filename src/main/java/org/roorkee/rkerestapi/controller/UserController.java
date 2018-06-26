package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.dao.AbstractDao;
import org.roorkee.rkerestapi.dao.UserDao;
import org.roorkee.rkerestapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController extends AbstractBaseController<User>{

    @Autowired
    private UserDao dao;

	@Override
	AbstractDao<User> getDao() {
		return dao;
	}
}
