package com.example.service;

import com.example.dto.TUser;

public interface TUserServiceAOP {
    public TUser getTUserById(Integer id);
    public int addUser(TUser tUser);
}
