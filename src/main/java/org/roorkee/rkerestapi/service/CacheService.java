package org.roorkee.rkerestapi.service;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.stdimpl.GCacheFactory;
import com.google.apphosting.api.ApiProxy;
import lombok.Data;
import org.roorkee.rkerestapi.controller.MailController;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

@Service
public class CacheService {

    private static final Logger log = Logger.getLogger(CacheService.class.getName());

    Cache cache;

    public CacheService(){
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            Map<Object, Object> properties = new HashMap<>();
            properties.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);
            properties.put(GCacheFactory.EXPIRATION_DELTA, TimeUnit.HOURS.toSeconds(1));
            cache = cacheFactory.createCache(properties);
        } catch (CacheException e) {
            log.info("Unable to initialize cache");
        }
    }

    public void put(String key, Object value){
        try {
            cache.put(key, value);
        }
        catch(ApiProxy.CallNotFoundException ex) {
            log.info("Unable to save data to cache");
        }
    }

    public Object get(String key){
        try{
            return cache.get(key);
        }
        catch(ApiProxy.CallNotFoundException ex) {
            log.info("Unable to read data from cache");
            return null;
        }
    }
}
