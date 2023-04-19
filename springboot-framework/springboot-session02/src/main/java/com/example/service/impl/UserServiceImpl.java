package com.example.service.impl;

import com.example.dto.TUser;
import com.example.mapper.TUserMapper;
import com.example.service.UserService;
import com.example.util.Customer;
import com.example.util.JWTUtil;
import com.example.vo.ResultVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    private static  final String  PREFIX = "demo:session:id:";

    private static  final Long EXPTIME = 30 * 60 * 1000L;

    //token会存放在redis和前端localstorage中，
    @Override
    public ResultVO login(String name, String pass) {
      TUser tUser =  tUserMapper.selectUserbyNameAndPass(name,pass);
      //针对当前得到的用户信息进行jwt的操作
      if(tUser != null){
          String tk = redisTemplate.opsForValue().get(PREFIX + tUser.getId());
          if(tk == null) {
              String token = JWTUtil.createJWT(String.valueOf(tUser.getId()), tUser, "login", EXPTIME);
              //把token传入到redis中
              redisTemplate.opsForValue().setIfAbsent(PREFIX + tUser.getId(), token, EXPTIME, TimeUnit.MILLISECONDS);
              return ResultVO.success("200", "登录成功", token);
          }else{
              return ResultVO.success("200", "您已经登录过，无需二次登录", tk);
          }
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

    @Override
    public ResultVO logout(Integer id) {
        boolean flag = redisTemplate.delete(PREFIX + id);
        if(flag){
            return ResultVO.success("200","用户注销成功");
        }else{
            return ResultVO.failure("500","注销失败");
        }
    }
}
