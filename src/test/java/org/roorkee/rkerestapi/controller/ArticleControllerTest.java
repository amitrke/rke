package org.roorkee.rkerestapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.dao.AbstractDao;
import org.roorkee.rkerestapi.dao.ArticleDao;
import org.roorkee.rkerestapi.entity.Article;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest extends AbstractBaseControllerTest<Article> {
	
	@MockBean
	private ArticleDao dao;
	
	ObjectMapper mapper;
	
    @Override
    public void test_post() {

    }
    
    @Before
    public void setup() {
    	mockEntity = createMockEntity();
    	when(dao.save(any(Article.class))).thenReturn(1L);
    	when(dao.get(1L)).thenReturn(mockEntity);
    	apiURL = "/api/articles/";
    }
    
    @Override @Test
	public void createPositive() throws Exception {
		super.createPositive();
	}
    
    @Override @Test
	public void getPositive() throws Exception {
		super.getPositive();
	}

	private Article createMockEntity() {
    	Article c = new Article();
        c.setDescription("Test Description ");
        c.setUserId(1L);
        c.setTitle("Test Title ");
        c.setFullText("Test Full Text ");
        c.setImageURL("Image URL ");
        c.setPriority(1L);
        return c;
    }

	@Override
	AbstractDao<Article> getDao() {
		return dao;
	}

}
