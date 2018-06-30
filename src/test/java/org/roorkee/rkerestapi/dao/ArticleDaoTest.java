package org.roorkee.rkerestapi.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleDaoTest extends AbstractBaseDaoTest<Article> {

    @Autowired
    private ArticleDao dao;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mockEntity = new Article();
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
    public void create_negative() {
        Article c = new Article();
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
        long out = dao.save(mockEntity);
        mockEntity.setId(out);
        assertThat(out).isNotNull();
        Article dbContent = dao.get(out);
        assertThat(dbContent).isEqualToComparingFieldByField(mockEntity);
        dbContent.setDescription("Test Description Edited");
        long out2 = dao.save(dbContent);
        assertThat(out2).isEqualTo(out);
        Article dbContent2 = dao.get(out2);
        assertThat(dbContent2.getDescription()).isEqualToIgnoringCase("Test Description Edited");
    }

    @Override
    protected AbstractDao<Article> getDao() {
        return dao;
    }
}
