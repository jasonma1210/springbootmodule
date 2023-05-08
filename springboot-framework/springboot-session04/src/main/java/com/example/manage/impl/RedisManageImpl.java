package com.example.manage.impl;

import com.example.manage.RedisManage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisManageImpl implements RedisManage {


    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    /**
     * 添加登录token
     *
     * @param id
     * @param value
     * @param TTL
     */
    @Override
    public void addLogin(Integer id, String value, Long TTL) {
           String key =  RedisManage.PREFIX + RedisManage.MIDDLE_LOGIN+id;
            redisTemplate.opsForValue().setIfAbsent(key,value,TTL, TimeUnit.SECONDS);
    }

    @Override
    public Object checkLoginUser(Integer id) {
        String key =  RedisManage.PREFIX + RedisManage.MIDDLE_LOGIN+id;
        return redisTemplate.opsForValue().get(key);
    }


    @Override
    public void delUser(Integer id) {
        String key =  RedisManage.PREFIX + RedisManage.MIDDLE_LOGIN+id;
        redisTemplate.delete(key);
    }


    /**
     * 双token验证，特点在于解决用户登录后session时间较短，同时用户可能
     * 在使用的过程中，由于刚好在需要使用的时候超过了最大的token时间
     * 使得直接重新登录的尴尬。
     * 解决方案：2个token，前置和后置；如果总时间30分钟；前置25分钟后置30分钟。
     * 每次请求都带2个token过去，当发现其中一个token失效，一个token还在说明已经属于
     * 25之后30分钟之前时间端，所以就自动重新刷新2个token，使得往后延期30分钟;
     * 如果2个token都在说说说明25钟之前，所以不需要再次生成；
     * 一旦没有token说明已经挂了，重新登录
     * @param key
     * @param value
     * @param TTL1
     * @param TTL2
     */
    @Override
    public void add2Token(String key, String value, Long TTL1, Long TTL2) {
       String  key1 =  RedisManage.PREFIX + RedisManage.MIDDLE_LOGIN+"PRE:"+key;
        String  key2 =  RedisManage.PREFIX + RedisManage.MIDDLE_LOGIN+"SUF:"+key;
        //前置的token
        redisTemplate.opsForValue().setIfAbsent(key1,value,TTL1, TimeUnit.SECONDS);   //25分钟
        redisTemplate.opsForValue().setIfAbsent(key2,value,TTL2, TimeUnit.SECONDS);    //30分钟
    }
}
