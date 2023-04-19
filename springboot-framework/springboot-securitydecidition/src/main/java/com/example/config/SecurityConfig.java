package com.example.config;

import com.example.config.filter.JsonUserNamePasswordAuthenticationFilter;
import com.example.config.handler.*;
import com.example.config.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 所有的配置信息都在该配置类中中进行定义，
 * 包括密码加密
 * 包括用户认证
 * 包括页面功能绑定等操作
 * 同时也可以在页面功能绑定操作过程中加入对应的自定义处理器，实现返回结果自定义化
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //权限拒绝处理逻辑
    @Resource
    CustomizeAccessDeniedHandler accessDeniedHandler;

    //匿名用户访问无权限资源时的异常
    @Resource
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    //会话失效(账号被挤下线)处理逻辑
    @Resource
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    //登出成功处理逻辑
    @Resource
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    //访问决策管理器
    @Resource
    CustomizeAccessDecisionManager accessDecisionManager;

    //实现权限拦截
    @Resource
    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Resource
    private CustomizeAbstractSecurityInterceptor securityInterceptor;

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public JsonUserNamePasswordAuthenticationFilter jsonUserNamePasswordAuthenticationFilter(){
        JsonUserNamePasswordAuthenticationFilter jsonUserNamePasswordAuthenticationFilter = new JsonUserNamePasswordAuthenticationFilter();
//        jsonUserNamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
//        jsonUserNamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return jsonUserNamePasswordAuthenticationFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //获取用户账号密码及权限信息
        return new UserDetailServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式（强hash方式加密）
        return new BCryptPasswordEncoder();
    }

    /**
     * 重写AuthenticationManager解决filter问题
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭防火墙csrf
        http.cors().and().csrf().disable();
        //认证
        http.authorizeRequests().
                //这里是用于后置添加新模块
                        withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
                        return o;
                    }
                }).
                //登出
                        and().logout().
                permitAll().//允许所有用户
                //因为前后端分离，所以必须使用logoutSuccessHandler来包装自定义的一个登出逻辑，其实就是返回一个rv
                        logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
                deleteCookies("JSESSIONID").//登出之后删除cookie
                //登入
//                        and().formLogin().
//                permitAll().//允许所有用户
//                successHandler(authenticationSuccessHandler).//登录成功处理逻辑
//                failureHandler(authenticationFailureHandler).//登录失败处理逻辑

                //异常处理(权限拒绝、登录失效等)
                        and()
                .exceptionHandling().
                accessDeniedHandler(accessDeniedHandler).//权限拒绝处理逻辑
                authenticationEntryPoint(authenticationEntryPoint).//匿名用户访问无权限资源时的异常处理
                //会话管理
                        and()
                .sessionManagement()
                .maximumSessions(1).//同一账号同时登录最大用户数
                expiredSessionStrategy(sessionInformationExpiredStrategy);//会话失效(账号被挤下线)处理逻辑
                     http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);
                     //直接替换掉原始的那个filter认证
                     http.addFilterAt(jsonUserNamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        System.out.println();
    }
}
