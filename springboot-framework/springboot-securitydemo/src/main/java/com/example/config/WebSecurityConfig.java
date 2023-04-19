package com.example.config;

import com.example.config.filter.JsonUserNamePasswordAuthenticationFilter;
import com.example.config.handler.*;
import com.example.config.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //登录成功处理逻辑
   @Resource
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //登录失败处理逻辑
   @Resource
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    //权限拒绝处理逻辑
   @Resource
    CustomizeAccessDeniedHandler accessDeniedHandler;

    //匿名用户访问无权限资源时的异常
   @Resource
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;



    //登出成功处理逻辑
   @Resource
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    /**
     * 允许匿名访问的地址
     */
    @Resource
    private PermitAllUrlProperties permitAllUrlProperties;


    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

//    //访问决策管理器
//   @Resource
//    CustomizeAccessDecisionManager accessDecisionManager;
//
//    //实现权限拦截
//   @Resource
//    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;


  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
      return new JwtAuthenticationTokenFilter();
  }

    @Bean
    public JsonUserNamePasswordAuthenticationFilter jsonUserNamePasswordAuthenticationFilter(){
        JsonUserNamePasswordAuthenticationFilter jsonUserNamePasswordAuthenticationFilter = new JsonUserNamePasswordAuthenticationFilter();
        return jsonUserNamePasswordAuthenticationFilter;
    }


    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        //获取用户账号密码及权限信息
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式（强hash方式加密）
        return new BCryptPasswordEncoder();
    }

    /**
     * 验证框架提交给自定的bean：userDetailsService来管理  ----认证用户
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    /**
     * 所有的实现权限管理的逻辑以及页面的跳转等操作都是在这里面配置的
     * 配置内容是通过and()方法来连接各个操作
     *
     *      * anyRequest          |   匹配所有请求路径
     *      * access              |   SpringEl表达式结果为true时可以访问
     *      * anonymous           |   匿名可以访问
     *      * denyAll             |   用户不能访问
     *      * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     *      * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     *      * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     *      * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     *      * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     *      * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     *      * permitAll           |   用户可以任意访问
     *      * rememberMe          |   允许通过remember-me登录的用户访问
     *      * authenticated       |   用户登录后可访问
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭防火墙csrf
        http.cors().and().csrf().disable();

        // 注解标记允许匿名访问的url
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        permitAllUrlProperties.getUrls().forEach(url -> registry.antMatchers(url).permitAll());

        //认证
        http.authorizeRequests().
//                antMatchers("/getUser").hasAuthority("query_user")
//                .antMatchers("/delUser").hasAuthority("del_user")
//                        .....  通过手动的方式实现对于url和premission的绑定非常不合适，因为请求越多，你写的越多，
                //万一老板改需求，你要加班到白头，你可以可以使用下面这种方式
//                antMatchers("/**").fullyAuthenticated(). //全绑定，所有的url都绑定了，完美。
//                但是有的时候需要只绑定部分请求，你就尬了
        //所以我们通过动态的方式进行绑定，实现方式在下面：
                        /*
                        我们需要实现一个AccessDecisionManager（访问决策管理器），
                        在里面我们对当前请求的资源进行权限判断，判断当前登录用户是否拥有该权限，
                        如果有就放行，如果没有就抛出一个"权限不足"的异常。
                        不过在实现AccessDecisionManager之前我们还需要做一件事，
                        那就是拦截到当前的请求，
                        并根据请求路径从数据库中查出当前资源路径需要哪些权限才能访问，
                        然后将查出的需要的权限列表交给AccessDecisionManager去处理后续逻辑。
                        那就是需要先实现一个SecurityMetadataSource，翻译过来是"安全元数据源"，
                        我们这里使用他的一个子类FilterInvocationSecurityMetadataSource。
                        在自定义的SecurityMetadataSource编写好之后，我们还要编写一个拦截器，
                        增加到Spring security默认的拦截器链中，以达到拦截的目的。
                         */

                //登出
                and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler).
//        //因为前后端分离，所以必须使用logoutSuccessHandler来包装自定义的一个登出逻辑，其实就是返回一个rv
//                    logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
//                    deleteCookies("JSESSIONID").//登出之后删除cookie
                //登入
                and().formLogin().
                    permitAll().//允许所有用户
                    successHandler(authenticationSuccessHandler).//登录成功处理逻辑
                    failureHandler(authenticationFailureHandler).//登录失败处理逻辑

                //异常处理(权限拒绝、登录失效等)
                    and()
                .exceptionHandling().
                    accessDeniedHandler(accessDeniedHandler).//权限拒绝处理逻辑
                    authenticationEntryPoint(authenticationEntryPoint).//匿名用户访问无权限资源时的异常处理
                //会话管理
                and()
                .sessionManagement()
                /**
                 * ALWAYS   总是创建HttpSession
                 *
                 * IF_REQUIRED   Spring Security只会在需要时创建一个HttpSession
                 *
                 * NEVER   Spring Security不会创建HttpSession，但如果它已经存在，将可以使用HttpSession
                 *
                 * STATELESS   Spring Security永远不会创建HttpSession，
                 */
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                .antMatchers("/login", "/register", "/anonymous").anonymous();
        http.addFilterAt(jsonUserNamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
