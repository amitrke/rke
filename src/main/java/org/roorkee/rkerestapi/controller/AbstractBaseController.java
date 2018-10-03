package org.roorkee.rkerestapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.roorkee.rkerestapi.dao.AbstractDao;
import org.roorkee.rkerestapi.entity.AbstractEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class AbstractBaseController<T extends AbstractEntity> {
	
	@PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    ResponseEntity<StringResponse> save(@RequestBody @Valid T entity) {
		Long id = getDao().save(entity);
		StringResponse sr = new StringResponse();
		sr.setResponse(id.toString());
		return new ResponseEntity<StringResponse>(sr, HttpStatus.OK);
	}

	@PostMapping(path= "/search", consumes = "application/json", produces = "application/json")
	ResponseEntity<List<T>> search(@RequestBody T entity) {
		List<T> results = getDao().search(entity);
		return new ResponseEntity<List<T>>(results, HttpStatus.OK);
	}
	
	@GetMapping(path="/", produces = "application/json")
	ResponseEntity<List<T>> list(){
		return new ResponseEntity<List<T>>(getDao().list(), HttpStatus.OK);
	}
	
	@GetMapping(path="/{id}", produces = "application/json")
	ResponseEntity<T> get(@PathVariable Long id) {
		T t = getDao().get(id);
		return new ResponseEntity<T>(t, HttpStatus.OK);
	}
	
	@DeleteMapping(path="/{id}")
    ResponseEntity<StringResponse> delete(@PathVariable Long id){
		getDao().delete(id);
		StringResponse sr = new StringResponse();
		sr.setResponse(id.toString());
		return new ResponseEntity<StringResponse>(sr, HttpStatus.OK);
	}
	
	abstract AbstractDao<T> getDao();
}
