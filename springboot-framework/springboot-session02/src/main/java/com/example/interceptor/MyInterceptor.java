package com.example.interceptor;

import com.example.dto.TUser;
import com.example.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Set;

@Component
public class MyInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("---------------------MyInterceptor init ok--------------------------------");
        //先从前端拿到token  ----- header中
        String token = request.getHeader("token");
        if(token != null){
            //判断是否过期
            boolean vaild = JWTUtil.checkToken(token);
              if(vaild){
                  Claims claims  = JWTUtil.parseJWT(token);
                  LinkedHashMap<String,Object> map = ( LinkedHashMap<String,Object>) claims.get("user");
                  Set<String> keys = map.keySet();
                  TUser user = new TUser();
                  for(String key : keys){
                      if(key.equals("id")) {
                          user.setId((Integer) map.get(key));
                          break;
                      }
                  }
                  //缓存走起,担心用户在操作时候万一点了注销操作，缓存是被清除的
                  String token1 = redisTemplate.opsForValue().get("demo:session:id:" + user.getId());
                  if(token1 != null){
                      request.setAttribute("user",user);
                      return true;
                  }else{
                      return false;
                  }

              }else {
                  return false;
              }
        }else {
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
