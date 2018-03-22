package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
public class ImageController {

    @Autowired FileStorageService fileStorageService;

    @PostMapping("/")
    public String put(HttpServletRequest req) throws IOException{
        return fileStorageService.uploadFile2(req.getInputStream(), req.getHeader("fileName"), "static.roorkee.org");
    }

}
