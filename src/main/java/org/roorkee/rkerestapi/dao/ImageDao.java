package org.roorkee.rkerestapi.dao;

import org.roorkee.rkerestapi.entity.Image;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDao extends AbstractDao<Image> {

    @Override
    String getKind() {
        Image i = new Image();
        return i.getKeyKind();
    }

    @Override
    Image newEntity() {
        return new Image();
    }
}
