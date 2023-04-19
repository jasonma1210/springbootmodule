package com.example.config.handler;


import com.example.util.ResultEnum;
import com.example.vo.ResultVO;
import com.example.util.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: jason
 * @Description: 登出成功处理逻辑
 * @Date Create in 2021/9/4 10:17
 */
@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
       //其实在实际开发最好还是需要添加比如删除缓存session的相关操作
        ResultVO result = ResultVO.success(ResultEnum.LOGOUT.getResCode(), ResultEnum.LOGOUT.getResMsg());
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(result));
    }
}
