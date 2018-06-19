package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.entity.AbstractEntity;

import java.util.List;

public abstract class AbstractBaseController<T extends AbstractEntity> {

    public abstract T save(T entity);

    public abstract List<T> list();

    public abstract T get(Long id);

    public abstract void delete(Long id);

}
