package org.roorkee.rkerestapi;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.entity.Image;
import org.roorkee.rkerestapi.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.ServletInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HttpRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileStorageService fileStorageService;

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
        File file = new File("src/test/java/org/roorkee/rkerestapi/book.png");
        InputStream is = new FileInputStream(file);

        InputStream mocIs = org.mockito.Mockito.mock(DelegatingServletInputStream.class);

        Image i = new Image();
        i.setFileName("static.roorkee.org/img/name.jpg");

        when(fileStorageService.uploadFile2(any(InputStream.class), eq("img/name.jpg"), eq("static.roorkee.org")))
                .thenReturn(i);

        MockMultipartFile fstmp = new MockMultipartFile("upload", file.getName(), "multipart/form-data",is);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/api/image/")
                .file(fstmp);
        request.header("filename", "img/name.jpg");


        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"owner\":null,\"status\":null,\"keyKind\":null}"));
    }

    @Test
    public void createContentShouldWork() throws Exception {
        String postBody = "{ \"id\": \"crystal\", \"title\": \"Crystal World\", \"imageURL\": \"assets/img/home/crystal_world_haridwar.jpg\", \"description\": \"The Largest amusement park close to Roorkee, it offers a variety of entertainment. Some of its offerings, like water sport, are immensely popular amongst children and grown-ups alike. A must visit and a popular destination for families.\", \"fullText\": \"The Largest amusement park close to Roorkee, it offers a variety of entertainment. Some of its offerings, like water sport, are immensely popular amongst children and grown-ups alike. A must visit and a popular destination for families.\" }";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/content/");
        request.content(postBody);
        request.header("Content-Type", "application/json");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{'message': 'Hello World!'}"));
    }
}
