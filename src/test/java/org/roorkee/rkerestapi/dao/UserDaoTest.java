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
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest extends AbstractBaseDaoTest<User>{
    
    @Autowired
    private UserDao dao;
    
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mockEntity = new User();
        mockEntity.mockObj();
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
    
    @Test
    public void get_positive(){
       super.get_positive();
    }
    
    @Test
    public void delete_positive(){
        super.delete_positive();
    }
    
    @Test
    public void edit_positive(){
        long out = getDao().save(mockEntity);
        mockEntity.setId(out);
        assertThat(out).isNotNull();
        User dbContent = dao.get(out);
        assertThat(dbContent).isEqualToComparingFieldByField(mockEntity);
        dbContent.setName("Test Name Edited");
        long out2 = dao.save(dbContent);
        assertThat(out2).isEqualTo(out);
        User dbContent2 = dao.get(out2);
        assertThat(dbContent2.getName()).isEqualToIgnoringCase("Test Name Edited");
    }

    @Override
    protected AbstractDao<User> getDao() {
        return dao;
    }
    
}