package com.example.service;

import com.example.vo.ResultVO;

public interface UserService {

    public ResultVO login(String name,String pass);

    public ResultVO  userInfo(Integer id);
}
