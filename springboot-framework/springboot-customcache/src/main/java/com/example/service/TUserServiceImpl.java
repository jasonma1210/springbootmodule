package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.dao.TUserMapper;
import com.example.dto.TUser;
import com.example.management.CaffieineManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TUserServiceImpl implements TUserService{

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private CaffieineManage caffieineManage;

    /**
     * 查的时候，先看缓存，如果缓存没有走数据库
     * @return
     */
    public TUser getTUserById(Integer id){
        TUser tUser = caffieineManage.getCacheByKey(String.valueOf(id), TUser.class);
        if(tUser != null){
            return tUser;
        }else{
            TUser u1 = new TUser();
            u1.setId(id);
            return tUserMapper.selectList(new QueryWrapper<TUser>(u1)).get(0);
        }

    }

    @Override
    public boolean addUser(TUser tUser) {
            int i = tUserMapper.insert(tUser);
            if(i>0) {
                caffieineManage.createOrUpdateCache(String.valueOf(tUser.getId()), tUser); //key=9 value={"侯臭臭"}
                return true;
            }else{
                return false;
            }

    }

    @Override
    public boolean updateUser(TUser tUser) {
        TUser t1 = new TUser();
        t1.setId(tUser.getId());
       int i = tUserMapper.update(tUser,new UpdateWrapper<>(t1));
        if(i>0) {
            caffieineManage.createOrUpdateCache(String.valueOf(tUser.getId()), tUser);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean delUser(Integer id) {
        TUser t1 = new TUser();
        t1.setId(id);
        int i = tUserMapper.delete(new QueryWrapper<>(t1));
        if(i > 0){
            caffieineManage.deleteCacheByKey(String.valueOf(id));
            return true;
        }else{
            return false;
        }

    }


}
