package com.example.aop;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;


/**
 * 该aop实现日志打印请求调用接口的信息
 */

@Aspect
@Component
//如果定义多Aop，数值越小越优先
@Order(1)
@Slf4j
public class ProjectAdvice {

    @Pointcut("execution(* com.example.controller.*.*(..))")
    public void aspect() {
    }

    @Around(value = "aspect()")
    public  Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("----------------环绕通知之前 的部分----------------");
        //通过反射获取被调用方法的Class
        Class type = pjp.getSignature().getDeclaringType();
        //获取类名
        String typeName = type.getSimpleName();
        //方法名
        String methodName = pjp.getSignature().getName();
        //获取参数列表
        Object[] args = pjp.getArgs();

        List<Object> ll = new ArrayList<>();
        for(Object o : args) {
            if(o == null){
                continue;
            }else{
                ll.add(o);
            }

        }
        //参数Class的数组,主要针对的是当前传入的参数可能并不是直接传递的对象或者属性，
        //而是直接用了HttpServletRequest或者HttpServletResponse的参数，所以针对这两种情况进行了处理
        Class[] clazz = new Class[ll.size()];
        for (int i = 0; i < ll.size(); i++) {
//            //springboot springmvc ------     HttpServetRequest  HttpServetResponse
//            if (ll.get(i).getClass().getCanonicalName().equals("org.apache.catalina.connector.RequestFacade")) {
//                clazz[i] = javax.servlet.http.HttpServletRequest.class;
//                continue;
//            }
//            if (ll.get(i).getClass().getCanonicalName().equals("org.apache.catalina.connector.ResponseFacade")) {
//                clazz[i] = javax.servlet.http.HttpServletResponse.class;
//                continue;
//            }

            clazz[i] = ll.get(i).getClass();
        }
        for(Class c : clazz){
            log.info(c.getCanonicalName());
        }


        //获得参数类型名称
        MethodSignature methodSignature =  (MethodSignature)pjp.getSignature();
        Class[] parameterTypes = methodSignature.getParameterTypes();
//        for(int i=0;i<parameterTypes.length;i++){
//            //log.info(parameterTypes[i].getCanonicalName());
//        }


        //通过反射获取调用的方法method
        Method method = type.getMethod(methodName, parameterTypes);
        //获取方法的参数
        Parameter[] parameters = method.getParameters();
        //拼接字符串，格式为{参数1:值1,参数2::值2}
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            String name = parameter.getName();
            sb.append(name).append(":").append(args[i]).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        //执行结果
        Object res;
        try {
            //执行目标方法，获取执行结果
            res = pjp.proceed();
            ObjectMapper objectMapper = new ObjectMapper();

            log.info("调用{}.{}方法成功，参数为[{}]，返回结果[{}]", typeName, methodName, sb.toString(), objectMapper.writeValueAsString(res));
            stopWatch.stop();
        } catch (Exception e) {
            log.error("调用{}.{}方法发生异常", typeName, methodName);
            //如果发生异常，则抛出异常
            throw e;
        } finally {
            log.info("调用{}.{}方法，耗时{}ms", typeName, methodName, stopWatch.getLastTaskTimeMillis());
        }
        log.info("----------------环绕通知之后 的部分----------------");
        //返回执行结果
        return res;
    }

}
