package org.roorkee.rkerestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MailService service;

    ObjectMapper mapper;

    @Before
    public void setup(){
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void testIncomingMailURL() throws Exception{
        MockHttpServletRequestBuilder request = post("/_ah/mail/admin@roorkee.org");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testOutbound() throws Exception {
        MessageRequest mockEntity = new MessageRequest();
        mockEntity.setToEmail("abc@gmail.com");
        mockEntity.setToName("John Doe");
        mockEntity.setTextBody("Text Body");
        mockEntity.setHtmlBody("<P>HTML Body</P>");
        mockEntity.setSubject("Test Subject");
        String postBody = mapper.writeValueAsString(mockEntity);

        MockHttpServletRequestBuilder request = post("/api/mail/out/");
        request.content(postBody);
        request.header("Content-Type", "application/json");

        this.mockMvc.perform(request).andDo(print()).andExpect(status().is2xxSuccessful());
    }


}
