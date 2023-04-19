package com.example.service;

import com.example.dto.TUser;
import com.example.vo.ResultVO;


public interface TUserService {

    public ResultVO insertTUser(TUser tUser);
    public ResultVO getAll(Integer page,Integer limit);

}
