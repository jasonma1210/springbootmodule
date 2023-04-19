package com.example.service.impl;

import com.example.RedisCacheAOPApplication;
import com.example.service.TbShoesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = RedisCacheAOPApplication.class)
class TbShoesServiceImplTest {

    @Resource
    private TbShoesService tbShoesService;

    @Test
    void insertAll() {
        tbShoesService.insertAll();
    }

    @Test
    void getTbShoesById() {
        System.out.println(tbShoesService.getTbShoesById(99413));
    }

    @Test
    void getTbShoesById01() {
        System.out.println(tbShoesService.getTbShoesById(99877));
    }
}