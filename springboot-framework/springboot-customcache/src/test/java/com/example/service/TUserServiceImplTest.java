package com.example.service;

import com.example.dto.TUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TUserServiceImplTest {

    @Resource
    private TUserService tUserService;

    @Test
    public void execute(){
        TUser tUser = new TUser();
        tUser.setId(9);
        tUser.setName("侯臭臭");
        tUser.setPass("112233");
        System.out.println( tUserService.addUser(tUser));  //sql   ---   cache
        System.out.println("-----------------------------------------------------");
        System.out.println(tUserService.getTUserById(9));   //cache
    }

//    @Test
//    public void get(){
//        System.out.println(tUserService.getTUserById(7));
//    }


}