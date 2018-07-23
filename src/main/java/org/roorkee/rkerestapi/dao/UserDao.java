package org.roorkee.rkerestapi.dao;

import org.roorkee.rkerestapi.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractDao<User> {

    @Override
    String getKind() {
        User u = new User();
        return u.getKeyKind();
    }

    @Override
    User newEntity() {
        return new User();
    }
}
