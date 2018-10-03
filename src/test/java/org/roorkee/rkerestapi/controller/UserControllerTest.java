package org.roorkee.rkerestapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.dao.AbstractDao;
import org.roorkee.rkerestapi.dao.UserDao;
import org.roorkee.rkerestapi.entity.User;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends AbstractBaseControllerTest<User>{
	
	@MockBean
	private UserDao dao;
	
	@Before
    public void setup() {
		super.setup();
		mockEntity = new User();
		mockEntity.mockObj();
		apiURL = "/api/users/";
		when(dao.save(any(User.class))).thenReturn(1L);
    	when(dao.get(1L)).thenReturn(mockEntity);
	}
	
	@Override @Test
	public void createPositive() throws Exception {
		super.createPositive();
	}
    
    @Override @Test
	public void getPositive() throws Exception {
		super.getPositive();
	}

	@Test
	public void searchPositive() throws Exception{
		super.createPositive();
		String postBody = "{\"gId\":\"113510510750160541112\"}";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(apiURL+"search");
		request.content(postBody);
		request.header("Content-Type", "application/json");
		this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json("{'response': '1'}"));
	}

	@Override
	AbstractDao<User> getDao() {
		return dao;
	}

}
