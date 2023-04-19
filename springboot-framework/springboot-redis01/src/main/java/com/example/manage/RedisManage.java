package com.example.manage;


import com.example.dto.TUser;

public interface RedisManage {

    public String addStr(String key,String value);


    public<V> String addHash(String key, TUser user);

    public String getStr(String key);

}
