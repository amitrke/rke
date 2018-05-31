package org.roorkee.rkerestapi.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GStoreAttr {

    public Class<?> type() default Object.class;

}
