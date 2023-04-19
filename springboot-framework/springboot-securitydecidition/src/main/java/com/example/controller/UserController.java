package com.example.controller;

import com.example.entity.SysUser;
import com.example.util.ResultEnum;
import com.example.vo.ResultVO;
import com.example.service.SysUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: jason
 * @Description:
 * @Date Create in 2021/8/28 19:34
 */
@RestController
public class UserController {
    @Autowired
    SysUserService sysUserService;

        @GetMapping("/getUser")
    public ResultVO getUser() {
        List<SysUser> users = sysUserService.queryAllByLimit(0, 100);
        return ResultVO.success(ResultEnum.SUCCESS.getResCode(), ResultEnum.SUCCESS.getResMsg(),users);
    }


    @PostMapping("/delUser")
    public ResultVO delUser(){
        return ResultVO.success(ResultEnum.SUCCESS.getResCode(), ResultEnum.SUCCESS.getResMsg());
    }

    @PostMapping("/updateUser")
    public ResultVO updateUser(){
        return ResultVO.success(ResultEnum.SUCCESS.getResCode(), ResultEnum.SUCCESS.getResMsg());
    }

    /**
     * 测试有参数的的情况判断元数据
     * @param name
     * @param pass
     * @return
     */
    @PostMapping("/addUser")
    public ResultVO addUser(String name,String pass){
        System.out.println(name+":"+pass);
        return ResultVO.success(ResultEnum.SUCCESS.getResCode(), ResultEnum.SUCCESS.getResMsg());
    }

    @PostMapping("/insertUser")
    public ResultVO insertUser(){
        return ResultVO.success(ResultEnum.SUCCESS.getResCode(), ResultEnum.SUCCESS.getResMsg());
    }

    @PostMapping("/register")
    public ResultVO register(User user){
        return ResultVO.success(ResultEnum.SUCCESS.getResCode(), ResultEnum.SUCCESS.getResMsg());
    }

}
