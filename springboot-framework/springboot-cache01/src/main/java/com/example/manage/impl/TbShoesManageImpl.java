package com.example.manage.impl;

import com.example.dto.TbShoes;
import com.example.manage.TbShoesManage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class TbShoesManageImpl implements TbShoesManage {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void mSet(Map<String,Object> map) {
        //string   setnx
                redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    @Override
    public void flushCurrentDB(){
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    @Override
    public TbShoes getCacheTbShoes(String key) {
        return (TbShoes) redisTemplate.opsForValue().get(key);
    }
}
