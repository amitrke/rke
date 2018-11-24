package org.roorkee.rkerestapi.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Photogallery extends AbstractEntity {

    @GStoreAttr
    private String imageURL;

}
