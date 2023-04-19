package com.example.mapper;

import com.example.dto.TUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TUserMapper {


    public TUser selectUserbyNameAndPass(@Param("name") String name,@Param("pass") String pass);

    public TUser userInfo(Integer id);
}
