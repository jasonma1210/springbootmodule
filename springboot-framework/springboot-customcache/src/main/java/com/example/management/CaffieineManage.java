package com.example.management;

import org.springframework.stereotype.Component;

/**
 * 作为一个独立的组件，实现各类缓存的操作的
 */

public interface CaffieineManage {
    /**
     * 添加或更新缓存
     *
     * @param key
     * @param value
     */
    public void createOrUpdateCache(String key, Object value) ;

    /**
     * 获取对象缓存
     *
     * @param key
     * @return
     */
    public <O> O getCacheByKey(String key, Class<O> t);

    /**
     * 根据key删除缓存
     *
     * @param key
     */
    public void deleteCacheByKey(String key);

}
