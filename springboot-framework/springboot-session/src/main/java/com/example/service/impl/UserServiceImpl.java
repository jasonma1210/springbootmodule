package com.example.service.impl;

import com.example.dto.TUser;
import com.example.mapper.TUserMapper;
import com.example.service.UserService;
import com.example.vo.ResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper tUserMapper;

    @Override
    public ResultVO login(String name, String pass) {
      TUser tUser =  tUserMapper.selectUserbyNameAndPass(name,pass);
      if(tUser != null){
          return ResultVO.success("200","登录成功",tUser);
      }else{
         return ResultVO.failure("500","登录失败");
      }
    }

    @Override
    public ResultVO userInfo(Integer id) {
        TUser tUser = tUserMapper.userInfo(id);
        if(tUser != null){
            return ResultVO.success("200","查询成功",tUser);
        }else{
            return ResultVO.failure("500","查询失败");
        }
    }
}
