package com.example.controller;

import com.example.dto.User;
import com.example.manage.RedisManage;
import com.example.vo.ResultVO;
import com.example.util.JWTUtil01;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

   private static User user =null;
    static {
        user = new User(1,"majian","123456");
    }

    @Resource
    private RedisManage redisManage;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping("/login")
    public ResultVO login(String username,String password){
            //1.实现用户名和密码的判断是否能够登录成功
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                //前置判断，目的就是如果用户已经登录过了，就没有必要进行二次登录操作
                String tk = (String)redisManage.checkLoginUser(user.getId());
                if(null != tk){
                    return ResultVO.success("201","该用户已经登录过，无需二次登录",tk);
                }else {
                    //2.登陆成功后，先去把当前用户的信息对应的内容解释成jwt生成token
                    String token = null;
                    token = JWTUtil01.createJWT(String.valueOf(user.getId()), username, "用户登录", null);
                    //3.把生成的token交给redis存放，并且格式是
                    //key: example:login:1
                    //value: token值
                    redisManage.addLogin(user.getId(), token, 60 * 30L);
                    //4.把token返回给前端,header
                    return ResultVO.success("200", "登录成功", token);
                }
            } else {
                return ResultVO.failure("500", "登录失败");
            }
    }

    /**
     * 根据当前登录的用户去查询该用户信息
     * @return
     */
    @SneakyThrows
    @GetMapping("/getUser")
    public ResultVO getUser(Integer id){

        String token = (String)redisManage.checkLoginUser(id);
        if(null == token){
            return ResultVO.failure("501","当前用户信息丢失，请重新登录");
        }else{
            //1.按照token去解析，拿到用户
            Claims claims = JWTUtil01.parseJWT(token);
            String username = claims.get("username", String.class);
            Integer ids = Integer.valueOf(claims.get("id",String.class));
            User user = new User(ids,username,"PROTECTED");
            return ResultVO.success("202","查询用户信息成功",user);
        }
    }

    @SneakyThrows
    @GetMapping("/logout")
    public ResultVO Logout(HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil01.parseJWT(token);
        Integer id = Integer.valueOf(claims.get("id",String.class));
        redisManage.delUser(id);
        return ResultVO.success("203","注销成功");

    }

    @RequestMapping("/login2Token")
    public ResultVO login2Token(String username,String password){
        //1.实现用户名和密码的判断是否能够登录成功
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
            //前置判断，目的就是如果用户已经登录过了，就没有必要进行二次登录操作
                //2.登陆成功后，先去把当前用户的信息对应的内容解释成jwt生成token
                String token  = JWTUtil01.createJWT(String.valueOf(user.getId()), username, "用户登录", 60*30*1000L);
                //3.把生成的token交给redis存放，并且格式是
                //key: example:login:1
                //value: token值
            //存放2个redis token
                    redisManage.add2Token(String.valueOf(user.getId()),token,60*25L,60*30L);
                //4.把token返回给前端,header
                return ResultVO.success("200", "登录成功", token);
            }
         else {
            return ResultVO.failure("500", "登录失败");
        }
    }

    /**
     * 模拟演示双token机制
     * @return
     */
    @GetMapping("/getall")
    public ResultVO getAll(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        Claims claims = JWTUtil01.parseJWT(token);
        Integer id = Integer.valueOf(claims.get("id",String.class));
        String username = claims.get("username", String.class);
        //先要判断有没有token
        Object preO = redisTemplate.opsForValue().get(RedisManage.PREFIX + RedisManage.MIDDLE_LOGIN + "PRE:" + id);
        Object sufO = redisTemplate.opsForValue().get(RedisManage.PREFIX + RedisManage.MIDDLE_LOGIN + "SUF:" + id);
        if(preO != null && sufO != null){
            return ResultVO.success("1111","成功",username);
        }else
        //前置token丢失，后置token还在，要延时
        if(preO == null && sufO != null){
                    String tokenSuff = (String)sufO;
                    //删除后置key
                    redisTemplate.delete(RedisManage.PREFIX + RedisManage.MIDDLE_LOGIN + "SUF:" + id);
                    //重新生成token
                    token = JWTUtil01.createJWT(String.valueOf(id),username,"用户登录",60*30*1000L);
                    redisManage.add2Token(String.valueOf(id),token,60*25L,60*30L);
                    return ResultVO.success("11111","成功续期");
        }
        else{
            return ResultVO.success("21111","用户失效请重新登录");
        }
    }
}
