package org.roorkee.rkerestapi.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.roorkee.rkerestapi.service.FileStorageService;
import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
public class ImageController{
	
	private static final String HDR_FILENAME = "filename";
    private static final String CLOUD_BUCKET = "static.roorkee.org";
    
    @Autowired FileStorageService fileStorageService;
    
    @PostMapping(path= "/", produces = "application/json")
	ResponseEntity<StringResponse> save(HttpServletRequest req) {
		InputStream inputStream;
        try {
            inputStream = req.getInputStream();
        }
        catch (IOException e){
            throw new RkeException(e);
        }
        
        if (StringUtils.isEmpty(req.getHeader(HDR_FILENAME))){
            throw new RkeException(new RuntimeException("Filename header missing in request."));
        }
        
        String resp = fileStorageService.uploadFile2(inputStream, req.getHeader(HDR_FILENAME), CLOUD_BUCKET);
        
        StringResponse sr = new StringResponse();
		sr.setResponse(resp);
		return new ResponseEntity<StringResponse>(sr, HttpStatus.OK);
	}
}
