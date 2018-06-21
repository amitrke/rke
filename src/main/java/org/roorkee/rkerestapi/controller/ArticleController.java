package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.dao.AbstractDao;
import org.roorkee.rkerestapi.dao.ArticleDao;
import org.roorkee.rkerestapi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin
public class ArticleController extends AbstractBaseController<Article>{

    @Autowired
    private ArticleDao dao;

	@Override
	AbstractDao<Article> getDao() {
		return dao;
	}

}
