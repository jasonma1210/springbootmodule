package com.example.service;

import com.example.dto.TUser;

import java.util.List;

public interface TUserService {
    /**
     * 查询数据
     * @return
     */
    public TUser getTUserById(Integer id);
    /**
     * 添加数据
     */
    public boolean addUser(TUser tUser);

    /**
     * 修改数据
     */
    public boolean updateUser(TUser tUser);

    /**
     * 删除数据
     */
    public boolean delUser(Integer id);
}
