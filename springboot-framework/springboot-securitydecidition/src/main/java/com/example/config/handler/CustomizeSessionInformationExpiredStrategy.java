package com.example.config.handler;

import com.example.util.ResultEnum;
import com.example.vo.ResultVO;
import com.example.util.JsonUtil;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: jason
 * @Description: 会话信息过期策略
 * @Date Create in 2021/9/4 9:34
 */
@Component
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        ResultVO result = ResultVO.failure(ResultEnum.USER_ACCOUNT_USE_BY_OTHERS.getResCode(),ResultEnum.USER_ACCOUNT_USE_BY_OTHERS.getResMsg());
        HttpServletResponse httpServletResponse = sessionInformationExpiredEvent.getResponse();
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonUtil.toJson(result));
    }
}
