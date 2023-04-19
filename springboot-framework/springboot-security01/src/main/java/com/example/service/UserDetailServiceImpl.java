package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserDetailServiceImpl implements UserDetailsService {


    @Resource(name="passwordEncoder")
    private PasswordEncoder passwordEncoder;


    //当登录成功后逻辑进入到这里，然后提供用户名给你，去取当前用户的信息以及权限集合
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//1. 查询数据库判断用户名是否存在，如果不存在抛出UsernameNotFoundException
            if(!username.equals( "majian")){
                throw new UsernameNotFoundException("用户名不存在");
            }
//把查询出来的密码进行解析,或直接把 password 放到构造方法中。
//理解:password 就是数据库中查询出来的密码，查询出来的内容不是 123
            String password = passwordEncoder.encode( "123456");
            return new
                    User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList (
                    "admin"));  //admin != role admin
    }
}
