package com.example.config.handler;

import com.example.entity.SysUser;

import com.example.service.SysUserService;
import com.example.util.JWTUtil01;
import com.example.util.JsonUtil;
import com.example.util.ResultEnum;
import com.example.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: jason
 * @Description: 登录成功处理逻辑
 * @Date Create in 2021/9/3 15:52
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    SysUserService sysUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //更新用户表上次登录时间、更新人、更新时间等字段
        User userDetails = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println();
        SysUser sysUser = sysUserService.selectByName(userDetails.getUsername());
        sysUser.setLastLoginTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUser(sysUser.getId());
        //把当前的时间对用户表中的登录时间，修改时间，登录修改内容人进行更新
        sysUserService.update(sysUser);

        //为了保证当前登录用户的信息不能直接明文的提交给前端，所以我们使用了jwt加密
        //这样只要登录后获得到该jwt的结果，然后绑定到对应的Header中，这个时候每次
        //用户去查询该对应的权限方法时候，直接加上token就可以判断当前是哪个用户信息，
        // 并根据该用户信息去处理对应的方法内容
        //实现jwt,最后一个设置为null，代表24小时过期
        String jwtToken = JWTUtil01.createJWT(sysUser.getId().toString(),sysUser.getUserName(),"loginUser",null);

        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展

        //返回json数据
     ResultVO result = ResultVO.success(ResultEnum.SUCCESS.getResCode(),ResultEnum.SUCCESS.getResMsg(),jwtToken);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(result));
    }
}
