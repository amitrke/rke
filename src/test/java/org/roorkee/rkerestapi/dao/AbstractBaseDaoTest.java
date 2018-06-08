package org.roorkee.rkerestapi.dao;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.roorkee.rkerestapi.entity.AbstractEntity;
import org.roorkee.rkerestapi.util.RkeException;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractBaseDaoTest {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    protected void setUp() throws Exception{
        helper.setUp();
    }

    protected void tearDown() throws Exception {
        helper.tearDown();
    }

    protected void create_positive() {
        AbstractEntity obj = createMockObj(1L);
        long out = getDao().save(obj);
        assertThat(out).isPositive();
    }

    protected void get_positive(){
        AbstractEntity obj = createMockObj(1L);
        long out = getDao().save(obj);
        obj.setId(out);
        assertThat(out).isNotNull();
        AbstractEntity dbContent = getDao().get(out);
        assertThat(dbContent).isEqualToComparingFieldByField(obj);
    }

    protected void delete_positive(){
        AbstractEntity obj = createMockObj(1L);
        long out = getDao().save(obj);
        assertThat(out).isNotNull();
        getDao().delete(out);
        try {
            AbstractEntity dbContent = getDao().get(out);
        }
        catch (RkeException e){
            assertThat(e).isNotNull();
        }
    }

    protected AbstractEntity createMockObj(long id){
        AbstractEntity entity = getDao().newEntity();
        entity.setCreated(new Date());
        entity.setStatus("Active");
        entity.setUserId(1L);
        return entity;
    }

    protected abstract AbstractDao getDao();
}
