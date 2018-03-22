package org.roorkee.rkerestapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.roorkee.rkerestapi.controller.ImageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired private ImageController imageController;

    @Test
    public void contextLoads() throws Exception{
        assertThat(imageController).isNotNull();
    }
}
