package org.roorkee.rkerestapi.entity;

import javax.validation.constraints.NotNull;

public class Photogallery extends AbstractEntity {

    @GStoreAttr
    @NotNull
    private String title;

    @GStoreAttr
    @NotNull
    private String imageURL;

}
