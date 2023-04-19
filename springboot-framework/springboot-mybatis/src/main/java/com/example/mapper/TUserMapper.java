package com.example.mapper;

import com.example.dto.TUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TUserMapper {


    @Insert("insert into  t_user(name,pass) values(#{name,jdbcType=VARCHAR},#{pass,jdbcType=VARCHAR})")
    public int insertTUser(TUser tUser);

    public List<TUser> getAll();
}
