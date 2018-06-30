package org.roorkee.rkerestapi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.roorkee.rkerestapi.entity.AbstractEntity;
import org.roorkee.rkerestapi.util.RkeException;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public abstract class AbstractBaseDaoTest<T extends AbstractEntity> {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
    protected T mockEntity;
    
    protected void setUp() throws Exception{
        helper.setUp();
    }

    protected void tearDown() throws Exception {
        helper.tearDown();
    }

    protected void create_positive() {
        long out = getDao().save(mockEntity);
        assertThat(out).isPositive();
    }

    protected void get_positive(){
        long out = getDao().save(mockEntity);
        mockEntity.setId(out);
        assertThat(out).isNotNull();
        AbstractEntity dbContent = getDao().get(out);
        assertThat(dbContent).isEqualToComparingFieldByField(mockEntity);
    }

    protected void delete_positive(){
        long out = getDao().save(mockEntity);
        assertThat(out).isNotNull();
        getDao().delete(out);
        try {
            getDao().get(out);
        }
        catch (RkeException e){
            assertThat(e).isNotNull();
        }
    }

    protected abstract AbstractDao<T> getDao();
}
