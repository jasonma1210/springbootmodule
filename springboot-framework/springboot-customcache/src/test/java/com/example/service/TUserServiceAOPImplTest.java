package com.example.service;

import com.example.dto.TUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class TUserServiceAOPImplTest {

    @Resource
    private TUserServiceAOP tUserServiceAOP;

    @Test
    public void t1(){
            tUserServiceAOP.getTUserById(1);
    }

    @Test
    public void t2(){
        TUser tUser = new TUser();
       // tUser.setId(9);
        tUser.setName("hihihih");
        tUser.setPass("6555543");
        tUserServiceAOP.addUser(tUser);
    }

}