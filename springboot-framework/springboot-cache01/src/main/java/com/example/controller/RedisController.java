package com.example.controller;


import com.example.dto.TbShoes;
import com.example.service.TbShoesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisController {

    @Resource
    private TbShoesService tbShoesService;

    @GetMapping("/get")
    public TbShoes get(@RequestParam("id") Integer id){
        return tbShoesService.getTbShoesById(id);
    }


    @GetMapping("/insertAll")
    public String insertAll(){
            tbShoesService.insertAll();
            return "success";
    }
}
