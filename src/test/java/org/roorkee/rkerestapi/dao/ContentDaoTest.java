package org.roorkee.rkerestapi.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentDaoTest extends AbstractBaseDaoTest<Content> {

    @Autowired
    private ContentDao dao;

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
    public void create() {
        Content c = new Content();
        c.setDescription("Test Description");
        c.setTitle("Test Title");
        c.setId("testId");
        c.setFullText("Test Full Text");
        c.setImageURL("Image URL");
        c.setPriority(1);
        dao.create(c);
    }
}
