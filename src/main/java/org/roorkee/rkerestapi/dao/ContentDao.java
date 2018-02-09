package org.roorkee.rkerestapi.dao;

import com.google.appengine.api.datastore.Key;
import org.roorkee.rkerestapi.entity.Content;
import org.springframework.stereotype.Repository;

@Repository
public class ContentDao extends AbstractDao<Content>{

    @Override
    String getKind() {
        Content c = new Content();
        return c.getKeyKind();
    }

    @Override
    Content newEntity() {
        return new Content();
    }

    @Override
    Key createKey(String id) {
        return null;
    }
}
