package com.example.controller;

import com.example.dto.TUser;
import com.example.service.UserService;
import com.example.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResultVO login(@RequestBody TUser tUser, HttpSession session){
        ResultVO rv = null;
        //传统实现上通过使用servlet中的session来实现对于对象的绑定操作丢给服务器上
        rv =  userService.login(tUser.getName(), tUser.getPass());
        TUser user = (TUser) rv.getResData();

        session.setAttribute("user",user);
        return rv;
    }

    //restful
    @GetMapping("/userinfo")
    public ResultVO getUserInfo( HttpSession session){
            TUser user = (TUser)session.getAttribute("user");
            Integer id = user.getId();
            return userService.userInfo(id);
    }
}
