package com.example.handle;

import com.example.util.JsonUtil;
import com.example.vo.ResultVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 自定义登录后的操作逻辑处理器
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json;charset=utf-8");
        User user = (User)authentication.getPrincipal();
        ServletOutputStream outputStream = response.getOutputStream();
        ResultVO rv = ResultVO.success("200", "登录成功", user);
        //json
        outputStream.write(JsonUtil.toJson(rv).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }
}
