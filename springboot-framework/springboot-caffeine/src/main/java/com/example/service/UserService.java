package com.example.service;

import com.example.dto.User;

/**
 * key  id
 * value   user
 */
public interface UserService {

    /**
     * 插入缓存
     */
    public User addUser(User user);

    /**
     * 查询缓存
     */
    public void getUser(Integer id);

    /**
     * 修改缓存
     */
    public User updateUser(User user);

    /**
     * 删除缓存
     */
    public void deleteUser(Integer id);
}
