package com.example.service.impl;

import com.example.dto.TUser;
import com.example.mapper.TUserMapper;
import com.example.service.TUserService;
import com.example.vo.ResultVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class TUserServiceImpl implements TUserService {

    @Resource
    private TUserMapper tUserMapper;

    
    @Override
    public ResultVO insertTUser(TUser tUser) {
        int i = tUserMapper.insertTUser(tUser);
        if( i > 0 ){
            return ResultVO.success("200","插入成功");
        }else{
            return ResultVO.failure("500","插入失败");
        }

    }

    @Override
    public ResultVO getAll(Integer page, Integer limit) {
        Page pages = PageHelper.startPage(page, limit);
        PageInfo pageInfo1 = new PageInfo<>(tUserMapper.getAll());
        return ResultVO.success("200","查询成功",pageInfo1);

    }

}
