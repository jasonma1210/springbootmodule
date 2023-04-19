package com.example.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {

    @Bean("jedis")
    public Jedis jedis(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接
        jedisPoolConfig.setMaxTotal(8);        // 最大空闲连
        jedisPoolConfig.setMaxIdle(8);         // 最小空闲连
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setMaxWaitMillis(200);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"localhost",6379,1000,"123456");
        return jedisPool.getResource();

    }
}
