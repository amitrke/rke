package org.roorkee.rkerestapi.dao;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.roorkee.rkerestapi.entity.AbstractEntity;

public class AbstractBaseDaoTest<T extends AbstractEntity> {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    protected void setUp() throws Exception{
        helper.setUp();
    }

    protected void tearDown() throws Exception {
        helper.tearDown();
    }
}
