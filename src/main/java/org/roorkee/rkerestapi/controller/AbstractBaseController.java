package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.dao.AbstractDao;
import org.roorkee.rkerestapi.entity.AbstractEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public abstract class AbstractBaseController<T extends AbstractEntity> {
	
	@PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    Long save(T entity) {
		return getDao().save(entity);
	}
	
	@GetMapping(path="/", produces = "application/json")
    abstract List<T> list();
	
	@GetMapping(path="/{id}", produces = "application/json")
    abstract T get(Long id);
	
	@DeleteMapping(path="/{id}")
    abstract void delete(Long id);
	
	abstract AbstractDao<T> getDao();
}
