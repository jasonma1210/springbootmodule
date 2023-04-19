package com.example.util;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Customer {
     String prefix() ;
     //是否设置过期时间默认是不没有过期
     long exp() default -1L;
}
