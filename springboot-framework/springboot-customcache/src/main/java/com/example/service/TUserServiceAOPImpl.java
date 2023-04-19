package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.aop.ann.CachePut;
import com.example.aop.ann.Cacheable;
import com.example.dao.TUserMapper;
import com.example.dto.TUser;
import com.example.management.CaffieineManage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TUserServiceAOPImpl implements TUserServiceAOP{

    @Resource
    private TUserMapper tUserMapper;

    /**
     * 先查缓存，缓存没有就去查数据库，如果缓存有了，是不是就可以直接执行下面内容
     * @param id
     * @return
     */
    @Cacheable(clz=TUser.class)
    public TUser getTUserById(Integer id){
            TUser u1 = new TUser();
            u1.setId(id);
            return tUserMapper.selectList(new QueryWrapper<TUser>(u1)).get(0);
    }

    /**
     * 先从数据库写出，然后写入缓存
     */
    @Override
    @CachePut(clz = TUser.class)
    public int addUser(TUser tUser) {
            int i = tUserMapper.insert(tUser);
        System.out.println(":::::::::::"+tUser);
        return i;

    }

//    @Override
//    public boolean updateUser(TUser tUser) {
//        TUser t1 = new TUser();
//        t1.setId(tUser.getId());
//       int i = tUserMapper.update(tUser,new UpdateWrapper<>(t1));
//        if(i>0) {
//            caffieineManage.createOrUpdateCache(String.valueOf(tUser.getId()), tUser);
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    @Override
//    public boolean delUser(Integer id) {
//        TUser t1 = new TUser();
//        t1.setId(id);
//        int i = tUserMapper.delete(new QueryWrapper<>(t1));
//        if(i > 0){
//            caffieineManage.deleteCacheByKey(String.valueOf(id));
//            return true;
//        }else{
//            return false;
//        }
//
//    }


}
