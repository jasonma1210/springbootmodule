package com.example.util;

import com.example.filter.JsonUserNamePasswordAuthenticationFilter;
import com.example.handle.MyAuthenticationFailureHandler;
import com.example.handle.MyAuthenticationSuccessHandler;
import com.example.handle.MyLogOutSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
//启用方法级别的认证 prePostEnabled=true表示可以使用@PreAuthorize和@PostAuthrize
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //官方不推荐，
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
    //加密的方式
    @Bean("passwordEncoder")
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private MyLogOutSuccessHandler myLogOutSuccessHandler;

    @Bean
    public JsonUserNamePasswordAuthenticationFilter jsonUserNamePasswordAuthenticationFilter(){
        JsonUserNamePasswordAuthenticationFilter jsonUserNamePasswordAuthenticationFilter = new JsonUserNamePasswordAuthenticationFilter();
        return jsonUserNamePasswordAuthenticationFilter;
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

//
//    /**
//     * 登录的管理，包含登录的操作，既然登录的操作，登录成功后输入用户名和密码拿到的是当前用户信息以及权限
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception
//    {
////        auth.inMemoryAuthentication()
////                .withUser("jason").password(passwordEncoder().encode("123456")).roles("admin");
////        auth.inMemoryAuthentication()
////                .withUser("majian").password(passwordEncoder().encode("654321")).roles("normal");
//    }

    /**
     * 所有的页面定义权限定义在此处实现
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 默认表单认证
//        http.formLogin()
//                .loginProcessingUrl("/login") //当发现/login 时认为是登录，需要执行 UserDetailsServiceImpl
//  //              .successForwardUrl("/main") //此处是 post 请求
//                //自定义成功返回逻辑的实现
//                .successHandler(myAuthenticationSuccessHandler)
////                .failureForwardUrl("/failure")
                    // failureUrl("/failure.html")
//                .failureHandler(myAuthenticationFailureHandler)
//                .loginPage("/login.html")
//                .usernameParameter("name")  //form中对应的表单名称是name pass
//                .passwordParameter("pass")
//                //登出的业务逻辑
//                .and()
//                .logout()
//                .logoutSuccessHandler(myLogOutSuccessHandler)
//                .deleteCookies("JSESSIONID");

        //使用自定义的登录配置
  http.addFilterAt(jsonUserNamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//  默认情况下对所有的请求都要拦截，除了login   logout
// 包括上面的login.html url 拦截
        http.authorizeRequests()
                .antMatchers("/login.html").permitAll() //login.html 不需要被认证
                .antMatchers("/failure").permitAll() // failure 不需要被认证
                .anyRequest().authenticated();//所有的请求都必须被认证。必须登录后才能访问。



//关闭 csrf 防护
        http.csrf().disable();
    }
}
