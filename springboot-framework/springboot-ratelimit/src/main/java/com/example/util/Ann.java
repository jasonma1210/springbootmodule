package com.example.util;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Ann {
    /**
     * 每次请求后最大的访问次数
     * @return
     */
    long count() default 2000L;

    /**
     * 最大过期时间
     * @return
     */
    long expTime() default 1L;

}
