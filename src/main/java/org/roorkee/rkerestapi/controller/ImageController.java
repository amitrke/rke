package org.roorkee.rkerestapi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.roorkee.rkerestapi.service.FileStorageService;
import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
public class ImageController{
	
	private static final String HDR_FILENAME = "filename";
	private static final String HDR_FOLDER = "folder";
    private static final String CLOUD_BUCKET = "up.roorkee.org";
    
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

        String filename = req.getHeader(HDR_FILENAME);
        if (!filename.matches("^upload/users/-?\\d*\\/(.+\\.(?i)(jpg|jpeg|JPG|JPEG))$")){
            throw new RkeException(new RuntimeException("User folder missing or file not a jpg."));
        }

        String resp = fileStorageService.uploadFile2(inputStream, filename, CLOUD_BUCKET);
        
        StringResponse sr = new StringResponse();
		sr.setResponse(resp);
		return new ResponseEntity<StringResponse>(sr, HttpStatus.OK);
	}

    @DeleteMapping(path="/", produces = "application/json")
    ResponseEntity<Boolean> delete(HttpServletRequest req){
        if (StringUtils.isEmpty(req.getHeader(HDR_FILENAME))){
            throw new RkeException(new RuntimeException("Filename header missing in request."));
        }
        boolean resp = fileStorageService.delete((String) req.getHeader(HDR_FILENAME), CLOUD_BUCKET);
        return new ResponseEntity<Boolean>(resp, HttpStatus.OK);
    }

    @GetMapping(path="/", produces = "application/json")
    ResponseEntity<List<String>> list(HttpServletRequest req){
    	if (StringUtils.isEmpty(req.getHeader(HDR_FOLDER))){
            throw new RkeException(new RuntimeException("Folder header missing in request."));
        }
    	return new ResponseEntity<List<String>>(fileStorageService.list(req.getHeader(HDR_FOLDER), CLOUD_BUCKET), HttpStatus.OK);
    }
}
