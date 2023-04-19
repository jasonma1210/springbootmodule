package com.example.filter;

import com.example.handle.MyAuthenticationFailureHandler;
import com.example.handle.MyAuthenticationSuccessHandler;
import com.example.util.JsonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义重写用户登录的过滤器
 * 实现从表单模式变成json raw模式
 */
@Slf4j
public class JsonUserNamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    /**
     * 显示配置
     * @param authenticationManager
     */
    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //要求必须方法和原始方法一致必须是POST
        if(!"POST".equals(request.getMethod())){
            throw  new RuntimeException("方法参数必须是Post");
        }else{
              //读取前端传过来的json raw流  body raw json
            ServletInputStream inputStream = request.getInputStream();
            BufferedReader bufferedReader = null;
            StringBuffer sb = new StringBuffer();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[1024];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    sb.append(charBuffer, 0, bytesRead);
                }
            } else {
                sb.append("");
            }
            bufferedReader.close();
            String jsonStr = sb.toString();  //{"username":"user1","password":"123456"}
            Map<String,String> map = new LinkedHashMap<>();
            //{username=user1,password=123456}
            map = (Map)JsonUtil.toObj(jsonStr);
            log.info("----------------------"+sb.toString()+":"+map.toString()+"---------------------------");
            String username = map.get(getUsernameParameter());  //majian
            String password = map.get(getPasswordParameter());   //123456
            //操作
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
            setDetails(request,usernamePasswordAuthenticationToken);
            setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
            setAuthenticationFailureHandler(myAuthenticationFailureHandler);
            return super.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } 

    }

    @Override
    public void setAuthenticationSuccessHandler( AuthenticationSuccessHandler authenticationSuccessHandler) {
        super.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }

    @Override
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler customizeAuthenticationFailureHandler) {
        super.setAuthenticationFailureHandler(customizeAuthenticationFailureHandler);
    }
}
