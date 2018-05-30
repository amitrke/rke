package org.roorkee.rkerestapi.dao;

import org.roorkee.rkerestapi.entity.Content;
import org.roorkee.rkerestapi.entity.Image;

public class ImageDao extends AbstractDao<Image> {

    @Override
    String getKind() {
        Image i = new Image();
        return i.getKeyKind();
    }

    @Override
    Image newEntity() {
        return null;
    }
}
