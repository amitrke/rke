package org.roorkee.rkerestapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.roorkee.rkerestapi.dao.ArticleDao;
import org.roorkee.rkerestapi.entity.Article;
import org.roorkee.rkerestapi.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/pub/refreshCache")
@CrossOrigin
public class StaticCacheController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ArticleDao articleDao;

    @Value("${app.upload.bucket}")
    private String cloudBucket;

    @GetMapping(produces = "application/json")
    ResponseEntity<String> refreshCache(){
        Article article = new Article();
        article.setStatus("published-to-homepage");
        List<Article> articles = articleDao.search(article);
        ObjectMapper mapper = new ObjectMapper();
        try {
            byte[] byteArray = mapper.writeValueAsBytes(articles);
            InputStream is = new ByteArrayInputStream(byteArray);
            fileStorageService.uploadFile2(is, "cache/home.json", cloudBucket);
        }
        catch (JsonProcessingException ex){
            System.out.println("Error ");
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }
}
