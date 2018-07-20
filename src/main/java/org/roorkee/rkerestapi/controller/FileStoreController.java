package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class FileStoreController {
	
	 @Autowired FileStorageService fileStorageService;
	 
}
