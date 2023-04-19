package com.example.aop;

import com.alibaba.fastjson2.JSON;
import com.example.dto.TbShoes;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class CacheAOP {

    @Resource
    private RedisTemplate<String, TbShoes> redisTemplate;

    //private static final String cachePrefix = "shopping:shoes:id:";
        //环绕通知处理
    @Around("@annotation(customer)")
    public Object around(ProceedingJoinPoint pjp, Customer customer) throws Throwable {

        Object obj = null;
        //业务:中添加sql处理，AOP作为缓存先进行判断，如果有，就直接走AOP，而不走数据处理，如果没有直接走数据处理。
        //1.拿到请求的参数值
        Object[] args = pjp.getArgs();
        Integer id = (Integer) args[0];
        String key = customer.prefix() + id;
        TbShoes tbShoes = redisTemplate.opsForValue().get(key);
            if(tbShoes != null){
                //不走业务直接结束
                obj = tbShoes;
            }else{
                //走业务
                obj = pjp.proceed();
                if(obj != null){
                redisTemplate.opsForValue().setIfAbsent(key,(TbShoes) obj,customer.exp(), TimeUnit.SECONDS);
                }
            }
        return obj;
    }
}
