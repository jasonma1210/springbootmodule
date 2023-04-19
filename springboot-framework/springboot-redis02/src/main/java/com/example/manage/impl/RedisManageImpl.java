package com.example.manage.impl;

import com.example.manage.RedisManage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisManageImpl implements RedisManage {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * String set
     * @param key
     * @param value
     */
    @Override
    public void set(String key, String value) {

        stringRedisTemplate.opsForValue().set(key,value);
    }

    /**
     * string get
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * string添加多个结果内容
     * @param map
     */
    @Override
    public void mSet(Map<String,String> map) {
        if(map != null) {
            Set<String> set = map.keySet();
            for(String key: set){
                stringRedisTemplate.opsForValue().append(key,map.get(key));
            }

        }
    }

    /**
     * 获得mget的多个结果
     * @param keys
     * @return
     */
    @Override
    public List<String> mGet(List<String> keys) {
       return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * setnx的操作
     * @param key
     * @param value
     * @param time
     */
    @Override
    public boolean setnx(String key, String value, Long time) {
        //setex  只有过期时间，但是还可以进行修改
         // stringRedisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
        boolean flag = false;
        if(time != null){
            //setnx + setex
            flag = stringRedisTemplate.opsForValue().setIfAbsent(key,value,time, TimeUnit.SECONDS);
        }else{
            //setnx
            flag = stringRedisTemplate.opsForValue().setIfAbsent(key,value);
        }
        return flag;
    }

    /**
     * 删除key
     * 可以定义多个key值
     * @param keys
     * @return
     */
    @Override
    public Long delete(List<String> keys) {
       //stringRedisTemplate.delete(key);
       return stringRedisTemplate.delete(keys);
    }

    /**
     * 单hash操作
     * hset person name majian   hset person age 40
     * @param key
     * @param value
     */
    @Override
    public void addHash(String key, Object hashKey,Object value) {
        stringRedisTemplate.opsForHash().put(key,hashKey,value);
    }

    @Override
    public Object getHash(String key,Object hashKey){
       return stringRedisTemplate.opsForHash().get(key,hashKey);
    }

    /**
     * hash的多值操作
     * mhset person name majian age 40 .....
     * @param key
     * @param map
     */
    @Override
    public void addMutiHash(String key, Map<Object, Object> map) {
        stringRedisTemplate.opsForHash().putAll(key,map);
    }

    /**
     * 针对hash的某个值进行递增或递减
     * @param key
     * @param hashKey
     * @param incr
     * @return
     */
    @Override
    public Long incrHash(String key,Object hashKey,Long incr){
          return  stringRedisTemplate.opsForHash().increment(key,hashKey,incr);
    }

    /**
     * hash nx
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    @Override
    public boolean hashnx(String key, Object hashKey,Object value){
        return stringRedisTemplate.opsForHash().putIfAbsent(key,hashKey,value);
    }

    /**
     * set add 操作
     * @param key
     * @param value
     * @return
     */
    @Override
    public long setSet(String key,String ...value){
       return stringRedisTemplate.opsForSet().add(key,value);
    }

    /**
     * 取出并移除
     * @param key
     * @param count 取出多少内容
     * @return
     */
    @Override
    public List<String> getSet(String key,long count){
        return stringRedisTemplate.opsForSet().pop(key,count);
    }

    /**
     * 判断set中是否包含该结果
     * @param key
     * @param value
     * @return
     */
    public boolean smembers(String key,Object value){
       return stringRedisTemplate.opsForSet().isMember(key,value);
    }

    /**
     * 并集
     */
        public Set<String> union(String key1,List<String> key2){
            return  stringRedisTemplate.opsForSet().union(key1,key2);
        }

    /**
     * 差集
     */

            public Set<String> difference(String key1,List<String> key2){
                return stringRedisTemplate.opsForSet().difference(key1,key2);
            }

    /**
     * 交集
     * @param key1
     * @param key2
     * @return
     */
    public Set<String> intersect(String key1,List<String> key2){
        return stringRedisTemplate.opsForSet().intersect(key1,key2);
    }

    //list的操作
    @Override
    public long listPush(String key,String element,Integer direct){
        long l = 0;
        if(direct == 0) {
            l =  stringRedisTemplate.opsForList().leftPush(key, element);
        }
        if(direct == 1){
            l =  stringRedisTemplate.opsForList().rightPush(key, element);
        }
        return l;
    }
    //list的操作
    @Override
    public String listPop(String key,Integer direct){
        String s = "";
        if(direct == 0) {
            s =  stringRedisTemplate.opsForList().leftPop(key);
        }
        if(direct == 1){
            s =  stringRedisTemplate.opsForList().rightPop(key);
        }
        return s;
    }

    @Override
    public List<String> getListRange(String key,long start,long end){
       return stringRedisTemplate.opsForList().range(key,start,end);
    }

    //sortedset
    @Override
    public boolean zadd(String key,double score,String member){
        return stringRedisTemplate.opsForZSet().add(key,member,score);
    }

    /**
     * 删除
     * @param key
     * @param start
     * @param end
     * @param value
     * @return
     */
    @Override
    public long zrem(String key,Long start,Long end,String... value){
        long l = 0;
        if(start !=null && end != null){
          l =   stringRedisTemplate.opsForZSet().removeRange(key,start,end);
        }
        if(value != null) {
          l=    stringRedisTemplate.opsForZSet().remove(key,value);
        }
        return l;
    }

    @Override
    public Map<Double,String>order(String key,double min,double max,long offset,long count){
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
                   Map<Double,String> map = new HashMap<>();
                        for(ZSetOperations.TypedTuple<String> tt : typedTuples){
                            map.put(tt.getScore(),tt.getValue());
                        }
                        return map;
    }

    //=====-===========================================================
@Override
    public void add(String key,Object obj){
        redisTemplate.opsForValue().set(key,obj);
    }

    @Override
    public Object redisGet(String key){
        return redisTemplate.opsForValue().get(key);
    }



}
