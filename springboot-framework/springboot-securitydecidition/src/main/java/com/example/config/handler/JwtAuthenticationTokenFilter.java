package com.example.config.handler;

//import com.example.util.JWTUtil;
import com.example.util.JWTUtil01;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author jianma
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    /**
     * 直接将我们前面写好的service注入进来，通过service获取到当前用户的权限
     */
    @Resource
    private UserDetailsService userDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //做些过滤处理，针对、register的请求直接返回true,不去做下面的拿token的操作
        String url = httpServletRequest.getRequestURI();
        String strUrl = null;
        System.out.println(url);  //register?id=1&name=majian&pass=123456
        if(url.indexOf("?") == -1){
                    strUrl = url;
        }else{
            strUrl = url.substring(0,url.indexOf("?"));
        }
        //后续所有的比较都是从数据库中去拿去，如果数据库里面有就可以继续往下走
        if ("/register".equals(strUrl)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        if("/login".equals(strUrl)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        else {
            // 获取当请求头中的token，其实这里多余，完全可以使用HttpServletRequest来获取
            String authToken = httpServletRequest.getHeader("token");

            // 获取到当前用户的account
            String account = JWTUtil01.parseJWT(authToken).get("username", String.class);
            System.out.println("自定义JWT过滤器获得用户名为" + account);

            // 当token中的username不为空时进行验证token是否是有效的token
            if (!"".equals(account) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // token中username不为空，并且Context中的认证为空，进行token验证

                // 获取到用户的信息，也就是获取到用户的权限
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(account);

                if (JWTUtil01.checkToken(authToken)) {   // 验证当前token是否有效

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    //将authentication放入SecurityContextHolder中
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            // 放行给下个过滤器
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
