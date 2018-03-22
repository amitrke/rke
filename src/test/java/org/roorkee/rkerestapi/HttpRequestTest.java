package org.roorkee.rkerestapi;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HttpRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void helloWorldShouldWork() throws Exception {
        this.mockMvc.perform(get("/api/content/hello")).andDo(print()).andExpect(status().isOk())
                                    .andExpect(content().json("{'message': 'Hello World!'}"));
    }

    @Test
    public void imageUploadShouldNotWork1() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/image/");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(content().json("{'message': 'java.lang.RuntimeException: Filename header missing in request.'}"));
    }

    @Test
    public void imageUploadShouldWork() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/image/");
        request.header("filename", "img/name.jpg");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(content().json("{'message': 'java.lang.RuntimeException: Filename header missing in request.'}"));
    }

}
