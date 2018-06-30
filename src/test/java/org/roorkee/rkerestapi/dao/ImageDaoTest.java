package org.roorkee.rkerestapi.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageDaoTest extends AbstractBaseDaoTest<Image>{

    @Autowired
    private ImageDao dao;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mockEntity = new Image();
        mockEntity.mockObj();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected AbstractDao<Image> getDao() {
        return dao;
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
}
