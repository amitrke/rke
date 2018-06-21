package org.roorkee.rkerestapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.dao.ArticleDao;
import org.roorkee.rkerestapi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest extends AbstractBaseControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ArticleDao dao;
	
    @Override
    public void test_post() {

    }
    
    @Test
    public void createContentShouldWork() throws Exception {
    	when(dao.save(any(Article.class))).thenReturn(1L);
        String postBody = "{ \"id\": \"crystal\", \"title\": \"Crystal World\", \"imageURL\": \"assets/img/home/crystal_world_haridwar.jpg\", \"description\": \"The Largest amusement park close to Roorkee, it offers a variety of entertainment. Some of its offerings, like water sport, are immensely popular amongst children and grown-ups alike. A must visit and a popular destination for families.\", \"fullText\": \"The Largest amusement park close to Roorkee, it offers a variety of entertainment. Some of its offerings, like water sport, are immensely popular amongst children and grown-ups alike. A must visit and a popular destination for families.\" }";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/articles/");
        request.content(postBody);
        request.header("Content-Type", "application/json");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{'message': 'Hello World!'}"));
    }
}
