package org.roorkee.rkerestapi.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        Content c = createMockContentObj(1L);
        long out = dao.create(c);
        assertThat(out).isPositive();
    }
    
    @Test
    public void create_negative() {
        Content c = new Content();
        long out = dao.create(c);
        assertThat(out).isPositive();
    }

    @Test
    public void get_positive(){
        Content c = createMockContentObj(1L);
        long out = dao.create(c);
        c.setId(out);
        assertThat(out).isNotNull();
        Content dbContent = dao.get(out);
        assertThat(dbContent).isEqualToComparingFieldByField(c);
    }

//    @Test
//    public void get_negative(){
//        String id = "tContent1";
//        Content c = createMockContentObj(1L);
//        String out = dao.create(c);
//        dao.
//    }

    @Test
    public void delete_positive(){
        try {
            Content dbContent = dao.get(2L);
        }
        catch(RkeException e){
            assertThat(e).isNotNull();
        }
    }

    private Content createMockContentObj(Long id){
        Content c = new Content();
        c.setDescription("Test Description "+id);
        c.setTitle("Test Title "+id);
        c.setFullText("Test Full Text "+id);
        c.setImageURL("Image URL "+id);
        c.setPriority(1);
        return c;
    }
}
