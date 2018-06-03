package org.roorkee.rkerestapi.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.entity.AbstractEntity;
import org.roorkee.rkerestapi.entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import org.roorkee.rkerestapi.util.RkeException;

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
    public void create_positive() {
        super.create_positive();
    }
    
    @Test
    public void create_negative() {
        Content c = new Content();
        long out = dao.save(c);
        assertThat(out).isPositive();
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
        Content obj = createMockObj(1L);
        long out = getDao().save(obj);
        obj.setId(out);
        assertThat(out).isNotNull();
        Content dbContent = dao.get(out);
        assertThat(dbContent).isEqualToComparingFieldByField(obj);
        dbContent.setDescription("Test Description Edited");
        long out2 = dao.save(dbContent);
        assertThat(out2).isEqualTo(out);
        Content dbContent2 = dao.get(out2);
        assertThat(dbContent2.getDescription()).isEqualToIgnoringCase("Test Description Edited");
    }

    @Override
    protected Content createMockObj(long id) {
        Content c = new Content();
        c.setDescription("Test Description "+id);
        c.setTitle("Test Title "+id);
        c.setFullText("Test Full Text "+id);
        c.setImageURL("Image URL "+id);
        c.setPriority(1);
        return c;
    }

    @Override
    protected AbstractDao getDao() {
        return dao;
    }
}
