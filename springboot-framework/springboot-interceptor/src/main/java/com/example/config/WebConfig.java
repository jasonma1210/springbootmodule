package com.example.config;

import com.example.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {


    @Bean
    public MyInterceptor myInterceptor(){
        return new MyInterceptor();
    }
    /**
     * 实现拦截器的配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor()).addPathPatterns("/*");
      //  registry.addInterceptor(myInterceptor()).addPathPatterns("/first").excludePathPatterns("/aaa");
    }
}
