package org.roorkee.rkerestapi.dao;

import org.roorkee.rkerestapi.entity.Article;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDao extends AbstractDao<Article>{

    @Override
    String getKind() {
        Article c = new Article();
        return c.getKeyKind();
    }

    @Override
    Article newEntity() {
        return new Article();
    }

}
