package org.roorkee.rkerestapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

	@Override
	AbstractDao<User> getDao() {
		return dao;
	}

}
