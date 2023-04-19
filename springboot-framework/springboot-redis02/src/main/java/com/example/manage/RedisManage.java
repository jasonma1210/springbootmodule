package com.example.manage;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisManage {

    //String 的操作
    public void set(String key,String value);

    public String get(String key);

    public void mSet(Map<String,String> map);
    public List<String> mGet(List<String> keys);

    public boolean setnx(String key,String value,Long time);

    public Long delete(List<String> keys);

    //hash的操作
    public void addHash(String key , Object hashKey,Object value);
    public void addMutiHash(String key,Map<Object,Object> map);

    public Long incrHash(String key,Object hashKey,Long incr);

    public boolean hashnx(String key, Object hashKey,Object value);
    public long setSet(String key,String ...value);
    public List<String> getSet(String key,long count);
    public boolean smembers(String key,Object value);
    public Set<String> union(String key1, List<String> key2);
    public Set<String> difference(String key1,List<String> key2);
    public Set<String> intersect(String key1,List<String> key2);
    public long listPush(String key,String element,Integer direct);
    public String listPop(String key,Integer direct);

    public List<String> getListRange(String key,long start,long end);
    public boolean zadd(String key,double score,String member);
    public long zrem(String key,Long start,Long end,String... value);
    public Map<Double,String>order(String key,double min,double max,long offset,long count);

    public void add(String key,Object obj);
    public Object redisGet(String key);

    public Object getHash(String key,Object hashKey);
}
