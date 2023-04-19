package com.example.config;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    /**
     * 缓存管理器的bean定义
     * @return
     */
    @Bean("cacheManager")
    public CacheManager cacheManager(){
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        //expireAfterWrite 最后一次写入后，经过固定时间过期
                        .expireAfterWrite(4, TimeUnit.SECONDS)
              //  expireAfterAccess 最后一次写入或访问后，经过固定时间过期
                .expireAfterAccess(5, TimeUnit.SECONDS)
                        //refreshAfterWrite 写入后，经过固定时间过期，下次访问返回旧值并触发刷新
                        .refreshAfterWrite(6, TimeUnit.SECONDS)
                        .recordStats()
                .initialCapacity(100)
                .maximumSize(1000));
        return cacheManager;
    }
}
