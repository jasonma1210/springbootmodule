package com.example.filter;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;


//@WebFilter(filterName = "webFirstFilter",urlPatterns = "/*")
@Component(value = "webFirstFilter")
@Log
public class WebFirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----------init ------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       log.info("------------------开启filter-----------------");
       filterChain.doFilter(servletRequest,servletResponse);
       log.info("----------------关闭filter-------------------");
    }
}
