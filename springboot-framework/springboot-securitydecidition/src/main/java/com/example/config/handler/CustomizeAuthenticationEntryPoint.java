package com.example.config.handler;


import com.example.util.ResultEnum;
import com.example.vo.ResultVO;
import com.example.util.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: jason
 * @Description: 用户未登录后出现的异常情况
 * @Date Create in 2021/9/3 21:35
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResultVO result = ResultVO.failure(ResultEnum.USER_NOT_LOGIN.getResCode(),ResultEnum.USER_NOT_LOGIN.getResMsg());
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(result));
    }
}
