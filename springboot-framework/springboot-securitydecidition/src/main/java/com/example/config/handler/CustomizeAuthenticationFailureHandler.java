package com.example.config.handler;

import com.example.util.ResultEnum;
import com.example.vo.ResultVO;
import com.example.util.JsonUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: jason
 * @Description: 登录失败处理逻辑
 * @Date Create in 2021/9/3 15:52
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json数据
        ResultVO result;
        if (e instanceof AccountExpiredException) {
            //账号过期
            result =ResultVO.failure(ResultEnum.USER_ACCOUNT_EXPIRED.getResCode(),ResultEnum.USER_ACCOUNT_EXPIRED.getResMsg());
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            result =ResultVO.failure(ResultEnum.USER_CREDENTIALS_ERROR.getResCode(),ResultEnum.USER_CREDENTIALS_ERROR.getResMsg());
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            result =ResultVO.failure(ResultEnum.USER_CREDENTIALS_EXPIRED.getResCode(),ResultEnum.USER_CREDENTIALS_EXPIRED.getResMsg());
        } else if (e instanceof DisabledException) {
            //账号不可用
            result =ResultVO.failure(ResultEnum.USER_ACCOUNT_DISABLE.getResCode(),ResultEnum.USER_ACCOUNT_DISABLE.getResMsg());
        } else if (e instanceof LockedException) {
            //账号锁定
            result =ResultVO.failure(ResultEnum.USER_ACCOUNT_LOCKED.getResCode(),ResultEnum.USER_ACCOUNT_LOCKED.getResMsg());
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result =ResultVO.failure(ResultEnum.USER_ACCOUNT_NOT_EXIST.getResCode(),ResultEnum.USER_ACCOUNT_NOT_EXIST.getResMsg());
        }else{
            //其他错误
            result =ResultVO.failure(ResultEnum.COMMON_FAIL.getResCode(),ResultEnum.COMMON_FAIL.getResMsg());
        }
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(result));
    }
}
