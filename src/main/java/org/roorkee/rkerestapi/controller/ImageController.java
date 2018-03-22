package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.service.FileStorageService;
import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    private static final String HDR_FILENAME = "filename";
    private static final String CLOUD_BUCKET = "static.roorkee.org";

    @Autowired FileStorageService fileStorageService;

    @PostMapping("/")
    public String put(HttpServletRequest req){

        InputStream fileInputStream;
        try {
            fileInputStream = req.getInputStream();
        }
        catch (IOException e){
            throw new RkeException(e);
        }

        if (StringUtils.isEmpty(req.getHeader(HDR_FILENAME))){
            throw new RkeException(new RuntimeException("Filename header missing in request."));
        }

        return fileStorageService.uploadFile2(fileInputStream, req.getHeader(HDR_FILENAME), CLOUD_BUCKET);
    }

}
