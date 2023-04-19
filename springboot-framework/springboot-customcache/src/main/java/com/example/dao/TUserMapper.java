package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dto.TUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TUserMapper extends BaseMapper<TUser> {

}