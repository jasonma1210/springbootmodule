package com.example.config.service;

import com.example.entity.SysPermission;
import com.example.entity.SysUser;
import com.example.service.SysPermissionService;
import com.example.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jason
 * @Description:
 * @Date Create in 2021/8/29 14:36
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //通过拿到用户和当前该用户的权限就可以包装成一共 UserDetails接口---User对象
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        System.out.println(username);
        //根据用户名查询用户
        //SELECT * FROM SYSUSER WHERE USERNAME =username;
        SysUser sysUser = sysUserService.selectByName(username);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (sysUser != null) {
            //获取该用户所拥有的权限
            List<SysPermission> sysPermissions = sysPermissionService.selectListByUser(sysUser.getId());
            // 声明用户授权
//            sysPermissions.forEach(sysPermission -> {
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
//                grantedAuthorities.add(grantedAuthority);
//            });
            for(SysPermission sysPermission: sysPermissions){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
                grantedAuthorities.add(grantedAuthority);
            }
        }
        //这个User不是我定义的，而是Ss给你写好的
        User user = new User(sysUser.getAccount(), sysUser.getPassword(), sysUser.getEnabled(), sysUser.getNotExpired(), sysUser.getCredentialsNotExpired(), sysUser.getAccountNotLocked(), grantedAuthorities);
        System.out.println(user);
        return user;
    }
}
