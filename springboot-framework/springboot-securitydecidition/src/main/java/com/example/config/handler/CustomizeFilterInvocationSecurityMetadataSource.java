package com.example.config.handler;

import com.example.entity.SysPermission;
import com.example.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @Author: jason
 * @Description: 元数据
 * @Date Create in 2021/9/3 21:06
 *  todo: 当前安全元数据源这个类实际上主要的目的就是通过当前的url来绑定对应的权限名  /queryUser   去寻找当前认证用户下的权限是否包含add_user权限名
 *          类似于如果纯粹的写入到配置文件中这种写法：antMatchers("/getUser").hasAuthority("query_user")
 *          同样这个和用户无关，主要的目的就是来绑定url和对应的权限code类型项，并实现成上面所描述的那样的效果
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
   //设定当前的配置路径对象，这个对象主要是绑定url对应的那个权限（premissioncode）
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    SysPermissionService sysPermissionService;

    /**
     * 1.登录成功，拿到用户信息---这面内容，这里不说
     * 2.当我访问某个url的时候，这个时候是不是可以拿到对应的url地址，localhost:8080/security/queryUser   uri   /queryUser
     * 3.通过uri的信息去查询在数据库中权限和url绑定表中对应的那个权限名？？？
     * 4.如果拿到欧ok,权限认证通过执行，如果没拿到就跳转到应的权限不足的handler去实现权限不足的实现内容体
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求url地址，注意这边这个地址如果涉及传入参数，localhost:8087/getUser?id=1也是被获得到的
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        System.out.println(requestUrl);  //test?a    queryUSer?name=aaaa&pas=bbb
        //这种情况适合非restful的url
        int inx = requestUrl.indexOf("?");
        String url = null;
        if(inx == -1){
            url = requestUrl;
        }else {
            url = requestUrl.substring(0, inx);
        }
        //查询具体某个接口的权限
        List<SysPermission> permissionList =  sysPermissionService.selectListByPath(url);
        if(permissionList == null || permissionList.size() == 0){
            //请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        String[] attributes = new String[permissionList.size()];
        for(int i = 0;i<permissionList.size();i++){
            attributes[i] = permissionList.get(i).getPermissionCode();
        }
        //attributes = ["query_user"]
        List<ConfigAttribute> list = SecurityConfig.createList(attributes);
        System.out.println(list);  //[query_user]
        return list;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
