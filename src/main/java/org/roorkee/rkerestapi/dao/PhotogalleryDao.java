package org.roorkee.rkerestapi.dao;

import org.roorkee.rkerestapi.entity.Photogallery;
import org.springframework.stereotype.Repository;

@Repository
public class PhotogalleryDao extends AbstractDao<Photogallery> {

    @Override
    String getKind() {
        Photogallery photogallery = new Photogallery();
        return photogallery.getKeyKind();
    }

    @Override
    Photogallery newEntity() {
        return new Photogallery();
    }
}
