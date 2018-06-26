package org.roorkee.rkerestapi.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.roorkee.rkerestapi.dao.AbstractDao;
import org.roorkee.rkerestapi.entity.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractBaseControllerTest<T extends AbstractEntity> {
	
	@Autowired
    protected MockMvc mockMvc;
	
	protected T mockEntity;
	protected String apiURL;
    
    ObjectMapper mapper;
    
    public void setup() {
    	mapper = new ObjectMapper();
    }
    
    public void createPositive() throws Exception{
    	ObjectMapper mapper = new ObjectMapper();
        String postBody = mapper.writeValueAsString(mockEntity);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(apiURL);
        request.content(postBody);
        request.header("Content-Type", "application/json");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{'response': '1'}"));
    }
    
    public void getPositive() throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
    	String postBody = mapper.writeValueAsString(mockEntity);
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(apiURL+"1");
    	this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
        	.andExpect(content().json(postBody));
    }
    
    abstract AbstractDao<T> getDao();
}
