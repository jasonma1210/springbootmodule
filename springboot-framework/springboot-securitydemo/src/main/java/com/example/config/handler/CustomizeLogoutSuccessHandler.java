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
        ResultVO result = ResultVO.success(ResultEnum.LOGOUT.getResCode(), ResultEnum.LOGOUT.getResMsg());
        //这里最好删除一下session
        //把当前token拿到，删除---实际上开发中通过缓存中存放的token值，进行判定，如果拿到对应的结果，说明可能存在该token,就拿该token取判断对应用户信息，
        //如果缓存中结果已经过期（当前登录信息有时间设置）或者被删除（我们自己选择退出），这个时候我们在缓存拿不到结果就说明注销成功了
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(result));
    }
}
