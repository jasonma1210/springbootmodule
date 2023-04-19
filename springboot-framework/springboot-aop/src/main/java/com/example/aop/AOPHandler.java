package com.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
@Order(2)
public class AOPHandler {

    //AOP切面:用来定义把当前aop类切入到对应的位置上（controller上）
    @Pointcut("execution(* com.example.controller.AOPController.getaop01(..))")
    public void pointCut(){

    }

    //第二种切面实现方式   注解式切面   定义一个注解，只要打上该注解的方法就会被执行AOP操作

    //通知
    @Around(value = "pointCut()")
    public Object around01(ProceedingJoinPoint pjp) throws Throwable {
        log.info("aop01的切面进入");
        Object proceed = pjp.proceed();//getaop01()这个方法
        log.info("aop01的切面退出");
        return proceed;
    }
    //第二种切面实现方式   注解式切面   定义一个注解，只要打上该注解的方法就会被执行AOP操作
    @Pointcut("@annotation(AOPAnn)")
    public void ann(){

    }

    @Around(value = "ann()")
    public Object around02(ProceedingJoinPoint pjp) throws Throwable{
        //spring提供的计时器-----
        StopWatch stopWatch =  new StopWatch();
        stopWatch.start();
        log.info("around02开始操作执行代码");
        Object proceed = pjp.proceed();//getaop02()这个方法
//        log.info("around02的切面退出");
        stopWatch.stop();
        log.info("around02的切面退出,总共花费时间是"+stopWatch.getLastTaskTimeMillis()+"毫秒");
        return proceed;

    }
}
