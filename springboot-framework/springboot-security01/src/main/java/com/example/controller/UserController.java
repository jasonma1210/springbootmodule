package com.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    //把/aaa /bbb 加入到admin role
    //把/bbb 加入到normal role
    //@PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/aaa")
    public String aaa(){
        return "aaaaaaaaaaaaaaaaaaaaaaaaaaa";
    }

  //  @PreAuthorize("hasAnyRole('admin','normal')")
    @GetMapping("/bbb")
    public String bbb(){
        return "bbbbbbbbbbbbbbbbbbbbbbbbbbb";
    }

    @PostMapping("/main")
    public String main(){
        return "mainmainmainmainmainmainmainmainmainmainmain";
    }
    @GetMapping("/failure")
    public String failure(){
        return "failurefailurefailurefailurefailurefailure";
    }

}
