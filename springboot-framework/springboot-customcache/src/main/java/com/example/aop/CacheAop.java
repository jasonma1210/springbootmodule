package com.example.aop;

import com.example.aop.ann.CacheEvict;
import com.example.aop.ann.CachePut;
import com.example.aop.ann.Cacheable;
import com.example.dto.TUser;
import com.example.management.CaffieineManage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
@Slf4j
public class CacheAop {

    @Pointcut("@annotation(com.example.aop.ann.Cacheable)")
    public void cacheable(){}

    @Pointcut("@annotation(com.example.aop.ann.CacheEvict)")
    public void cacheEvict(){}

    @Pointcut("@annotation(com.example.aop.ann.CachePut)")
    public void cachePut(){}

    @Resource
    private CaffieineManage caffieineManage;

    @SneakyThrows
    @Around(value = "cacheable()")
    public TUser cacheAround(ProceedingJoinPoint pjp){
        log.info("------------");
        //通过反射获取被调用方法的Class
        Class type = pjp.getSignature().getDeclaringType();
        //获取类名
        String typeName = type.getSimpleName();
        //方法名
        String methodName = pjp.getSignature().getName();
        //方法的限定名称  ，就可以请求该方法对应头部是否存在对应的注解
        String methoddeclName = pjp.getSignature().getDeclaringTypeName();
        //获得参数类型名称
        MethodSignature methodSignature =  (MethodSignature)pjp.getSignature();
        Class[] parameterTypes = methodSignature.getParameterTypes();

        //获得方法上表示的注解的属性值
        Method method1 = methodSignature.getMethod();
        //获取注解
        Cacheable cacheable = method1.getAnnotation(Cacheable.class);

        Class clz = null;
        if(cacheable != null){
             clz = cacheable.clz();
        }

        //获取参数列表
        Object[] args = pjp.getArgs();
        //通过反射获取调用的方法method
        Method method = type.getMethod(methodName, parameterTypes);
        //获取方法的参数
        Parameter[] parameters = method.getParameters();

        String key = args[0].toString();
        log.info("key:"+key);
        //key id
        //value 对象的类型
        //caffieineManage.getCacheByKey(key,获得对应的value类型);

        Object cacheByKey = caffieineManage.getCacheByKey(key, clz);
        if(null == cacheByKey){
           return (TUser) pjp.proceed();
        }
        return null;
    }

    @AfterReturning(value = "cachePut()",returning = "result")
    public boolean afterReturning(JoinPoint pjp, Object result){
        log.info("进入cachePut ----AfterReturning切面");
        int res = Integer.valueOf(result.toString());
        if(res>0){
            //操作缓存的写入
            Object[] args = pjp.getArgs();

            TUser user = (TUser)args[0];
                caffieineManage.createOrUpdateCache(String.valueOf(user.getId()),user);
            log.info("缓存成功");
                return true;
        }else{
            log.info("数据没有操作成功，缓存也没进去");
            return false;
        }

    }
}
