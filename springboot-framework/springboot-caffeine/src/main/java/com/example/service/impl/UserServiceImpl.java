package com.example.service.impl;

import com.example.dto.User;
import com.example.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class UserServiceImpl implements UserService {

    //数据模拟
    Map<String,User> maps = new ConcurrentHashMap<>();

    @Override
    //添加缓存
    @CachePut(key = "#user.id")   //key==user.id   value = user
    public User addUser(User user) {
        maps.put(String.valueOf(user.getId()),user);
        return maps.get(String.valueOf(user.getId()));
    }

    @Override
    //查询
    @Cacheable(key = "#id")
    public void getUser(Integer id) {
        User user = maps.get(String.valueOf(id));
        System.out.println("查询用户："+user);

    }

    @Override
    //添加或修改
    @CachePut(key = "#user.id")
    public User updateUser(User user) {
        String key = String.valueOf(user.getId());
      if(maps.containsKey(key)){
                        maps.get(key).setId(user.getId());
                        maps.get(key).setName(user.getName());
                        maps.get(key).setPass(user.getPass());
                        return user;
        }else{
          return null;
      }
    }

    @Override
    //删除
    @CacheEvict(key = "#id")
    public void deleteUser(Integer id) {
        String key = String.valueOf(id);
        if(maps.containsKey(key)){
            maps.remove(key);
        }
        System.out.println("移除成功");
    }
}
