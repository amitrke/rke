package org.roorkee.rkerestapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.dao.AbstractDao;
import org.roorkee.rkerestapi.dao.ImageDao;
import org.roorkee.rkerestapi.entity.Image;
import org.roorkee.rkerestapi.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImageControllerTest {
	
	@MockBean
    private FileStorageService fileStorageService;
	
	@Autowired
    private MockMvc mockMvc;
	
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

        String i = "static.roorkee.org/img/name.jpg";

        when(fileStorageService.uploadFile2(any(InputStream.class), eq("img/name.jpg"), eq("static.roorkee.org")))
                .thenReturn(i);

        MockMultipartFile fstmp = new MockMultipartFile("upload", file.getName(), "multipart/form-data",is);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/api/image/")
                .file(fstmp);
        request.header("filename", "img/name.jpg");


        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"response\":\"static.roorkee.org/img/name.jpg\"}"));
    }
}
