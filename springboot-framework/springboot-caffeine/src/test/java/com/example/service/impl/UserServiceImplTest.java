package com.example.service.impl;

import com.example.CacheApplication;
import com.example.dto.User;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CacheApplication.class)
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void execute() throws InterruptedException {
        User u1 = new User();
        User u2 = new User();
        u1.setId(1);
        u1.setName("aa");
        u1.setPass("123456");
        u2.setId(2);
        u2.setName("bb");
        u2.setPass("654321");
        System.out.println("添加用户成功："+userService.addUser(u1));
        System.out.println("添加用户成功："+userService.addUser(u2));
        System.out.println("-------------------------");
        userService.getUser(2);
        System.out.println("-------------------------");
        userService.deleteUser(1);
        System.out.println("-------------------------");
        userService.getUser(1);



    }
}