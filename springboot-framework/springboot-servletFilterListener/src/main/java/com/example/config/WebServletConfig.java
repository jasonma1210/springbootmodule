package com.example.config;


import com.example.filter.WebFirstFilter;
import com.example.listener.MyListener;
import com.example.servlet.MyServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置类     xml   把所有的都交给spring来作为bean管理  实现对于serlvet中的三大组件变成bean来进行管理和操纵
 * 在实际开发中最常见的就是用filter
 */
@Configuration
public class WebServletConfig {
    /**
     * 注册servlet
     * @param myServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet( @Qualifier("myServlet") MyServlet myServlet) {

        //@WebServlet(value="myServlet",urlPattern="/myServlet")
        ServletRegistrationBean registrationBean =

                new ServletRegistrationBean(myServlet, "/myServlet");

        return registrationBean;

    }

    /**
     * 过滤器的bean
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean getFilter(@Qualifier("webFirstFilter") WebFirstFilter filter) {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        List<String> list = new ArrayList<>();
        list.add("/*");
        registrationBean.setUrlPatterns(list);
        return registrationBean;

    }

    /**
     * listener监听器的bean
     * @param myListener
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean getServletListener(@Qualifier("myListener") MyListener myListener) {
        ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean(myListener);
        return registrationBean;

    }

}
