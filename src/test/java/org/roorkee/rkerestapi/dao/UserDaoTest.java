package org.roorkee.rkerestapi.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.roorkee.rkerestapi.entity.User;
import org.roorkee.rkerestapi.dao.UserDao;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest extends AbstractBaseDaoTest<User>{
    
    @Autowired
    private UserDao dao;
    
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void create_positive() {
        super.create_positive();
    }
    
    @Override
    protected User createMockObj(long id) {
        User u = new User();
        u.setEmail("abc@def.com");
        u.setName("Test Name");
        u.setImageURL("/abc/def/a.jpg");
        u.setIndicator("Active");
        return u;
    }

    @Override
    protected AbstractDao getDao() {
        return dao;
    }
    
}