package org.roorkee.rkerestapi.controller;

import net.bytebuddy.pool.TypePool;
import org.roorkee.rkerestapi.dao.AbstractDao;
import org.roorkee.rkerestapi.dao.PhotogalleryDao;
import org.roorkee.rkerestapi.entity.Photogallery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/photogallery")
@CrossOrigin
public class PhotogalleryController extends AbstractBaseController<Photogallery> {

    @Autowired
    private PhotogalleryDao dao;

    @Override
    AbstractDao<Photogallery> getDao() {
        return dao;
    }
}
