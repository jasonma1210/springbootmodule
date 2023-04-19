package com.example.interceptor;

import com.example.util.Ann;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static  final String PREFIX = "springboot:ratelimit:";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String methodName = handlerMethod.getMethod().getName();   //aaa
            String key = PREFIX + methodName;   //springboot:ratelimit:aaa
            String cache = stringRedisTemplate.opsForValue().get(key);

            if(cache != null){
                        Integer count = Integer.parseInt(cache);
                        if(count > 1){
                            //递减1
                            stringRedisTemplate.opsForValue().decrement(key);
                            return true;
                        }else{
                            byte[] bytes = ("超过最大请求数，请稍后再试！！！！").getBytes(StandardCharsets.UTF_8);
                            out.write(bytes);
                            out.flush();
                            return false;
                        }
            }else{
                    //创建缓存
                Ann ann = handlerMethod.getMethod().getAnnotation(Ann.class);
                long time = ann.expTime();
                long count = ann.count();
                stringRedisTemplate.opsForValue().set(key,String.valueOf(count),time, TimeUnit.SECONDS);
                return true;
            }

        }else{
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
