package com.example.manage.impl;


import com.example.Main;
import com.example.dto.TUser;
import com.example.manage.RedisManage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes= Main.class)
class RedisManageImplTest {

    @Resource
    private RedisManage redisManage;

    @Test
    void addStr() {
        System.out.println(redisManage.addStr("ddddddd","121232dsadsad"));
    }

    @Test
    void addHash() {
        TUser user = new TUser();
        user.setName("sssweqwerew");
        user.setPass("654321");
        System.out.println(redisManage.addHash("user",user));
    }

    @Test
    void getStr() {
        System.out.println(redisManage.getStr("ddddddd"));
    }
}