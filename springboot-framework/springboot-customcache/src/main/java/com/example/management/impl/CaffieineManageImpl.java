package com.example.management.impl;

import com.example.management.CaffieineManage;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定义具体的实现类，该实现类主要就是对增删改查的缓存操作进行处理
 */
@Component
@Slf4j
public class CaffieineManageImpl implements CaffieineManage {

    @Resource
    private Cache<String, Object> caffeineCache;

    @Override
    public void createOrUpdateCache(String key, Object value) {
       log.info("--------缓存添加或更新操作---------------");
                caffeineCache.put(key,value);
    }

    @Override
    public <T> T getCacheByKey(String key, Class<T> t) {
       T t1= (T)caffeineCache.asMap().get(key);
            if(t1 == null){
               log.info("当前缓存中没有结果，走的是具体的查询");
            }

        return t1;
    }

    @Override
    public void deleteCacheByKey(String key) {
       log.info("缓存删除操作");
            caffeineCache.asMap().remove(key);
    }
}
