package com.example.manage.impl;

import com.example.dto.TUser;
import com.example.manage.RedisManage;
import com.example.util.JedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class RedisManageImpl implements RedisManage {


    @Autowired
    @Qualifier("jedis")
    private Jedis jedis;

    @Override
    public String addStr(String key, String value) {
        return jedis.set(key,value);
    }

    @Override
    public<V>  String addHash(String key,TUser user) {
        Map<String,String> map  = new HashMap<>();
                    map.put("name",user.getName());
                    map.put("pass",user.getPass());
                return   jedis.hmset(key,map);
    }

    @Override
    public String getStr(String key) {
        return jedis.get(key);
    }


}
