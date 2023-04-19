package com.example.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
   private static JedisPoolConfig jedisPoolConfig = null;
    static {
        jedisPoolConfig = new JedisPoolConfig();
        // 最大连接
        jedisPoolConfig.setMaxTotal(8);        // 最大空闲连
        jedisPoolConfig.setMaxIdle(8);         // 最小空闲连
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setMaxWaitMillis(200);
    }
    public static Jedis getInstance(){
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"localhost",6379,1000,"123456");
            return jedisPool.getResource();
    }
}


//JedisUtil.getInstance()