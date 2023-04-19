package com.example.controller;

import com.example.service.TAddressService;
import com.example.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MysqlController {
    @Resource
    private TAddressService tAddressService;
    @GetMapping("/selectbyuid")
    public ResultVO selectbyuid(@RequestParam("id") Integer id){
        return   tAddressService.selectAddressByUid(id);
    }
}
