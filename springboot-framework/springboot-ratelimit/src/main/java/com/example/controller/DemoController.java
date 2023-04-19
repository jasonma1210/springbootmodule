package com.example.controller;

import com.example.util.Ann;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {



    @GetMapping("/aaa")
    @Ann(count = 5l,expTime = 30)
    public String aaa(){
        return "aaaaaaaaaa";
    }

    @GetMapping("/login")
   // @Ann(count = 5l,expTime = 30)
    public String login(){
        return "login";
    }
}
