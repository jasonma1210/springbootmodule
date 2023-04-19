package com.example.controller;

import com.example.aop.AOPAnn;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@Slf4j
public class AOPController {
//如果不是用lombok的时候，
//需要自己定义日志工厂来实现日志信息，该日志工厂是给谁打上日志就加载哪个类
//  final static Logger logger = LoggerFactory.getLogger(AOPController.class);

    @GetMapping("/getaop01")
    public String getaop01(){
        log.info("你好aop");
        return "success";
    }

    @GetMapping("/getaop02")
    @AOPAnn
    public String getaop02(){
        log.info("注解的aop实现");
        return "success";
    }


    @GetMapping("/getaop03")
    public String getaop03(String name){
        log.info("日志格式测试："+name);
        return "success";
    }

    @GetMapping("/getaop04")
    public String getaop04(String name, HttpServletRequest request){
        String ip = request.getRemoteAddr();
        log.info("日志格式测试："+name+":"+ip);
        return "success";
    }
}
