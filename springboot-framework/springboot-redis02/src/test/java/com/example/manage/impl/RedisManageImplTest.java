package com.example.manage.impl;

import com.example.Redis02Main;
import com.example.dto.Goods;
import com.example.dto.TUser;
import com.example.manage.RedisManage;
import com.example.vo.GetCartVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = Redis02Main.class)
class RedisManageImplTest {

    @Resource
    private RedisManage redisManage;

    @Test
    void set() {
        redisManage.set("name","Jason");
    }

    @Test
    void get() {
        System.out.println(redisManage.get("name"));
    }

    @Test
    void mSet() {
        Map<String,String> map = new HashMap<>();
        map.put("aaa","123");
        map.put("bbb","456");
      redisManage.mSet(map);
    }

    @Test
    void mGet() {
        List<String> keys = new ArrayList<>();
        keys.add("aaa");
        keys.add("bbb");
        List<String> ll = redisManage.mGet(keys);
        for(String s: ll){
            System.out.println(s);
        }
    }

    @Test
    void setnx() throws InterruptedException {
        System.out.println(redisManage.setnx("ps5","sony",2L));
        System.out.println(redisManage.setnx("ps5","huaqiangbei",2L));
        TimeUnit.SECONDS.sleep(2);
        System.out.println(redisManage.setnx("ps5","huaqiangbei",10L));
    }

    @Test
    void setObj() throws InterruptedException {
        TUser user = new TUser();
        user.setId(1);
        user.setName("马健");
        user.setPass("123456");
        redisManage.add("user",user);
        TimeUnit.SECONDS.sleep(2L);
        System.out.println(redisManage.redisGet("user"));

    }

    @Test
    public void setGoods() throws Exception{
        Goods goods = new Goods();
        goods.setId(1);
        goods.setGoodsName("iphone14promax");
        goods.setPrice(new BigDecimal("9899.00"));
        goods.setStore(10000L);
        redisManage.add("shopping:goods:id:1",goods);
    }

    @Test
    public void setCart() throws Exception{
            redisManage.addHash("shopping:cart:id:9527","1","1");
    }
    @Test
    public void incrGoodsNum() throws Exception{
        System.out.println(redisManage.incrHash("shopping:cart:id:9527","1",1L));
    }
    @Test
    public void showCartInfo() throws Exception{
       Goods goods =  (Goods)redisManage.redisGet("shopping:goods:id:1");
       Long count =  Long.parseLong((String)redisManage.getHash("shopping:cart:id:9527","1"));
        GetCartVO getCartVO = new GetCartVO();
        getCartVO.setId(goods.getId());
        getCartVO.setGoodsName(goods.getGoodsName());
        getCartVO.setPrice(goods.getPrice());
        getCartVO.setStore(goods.getStore());
        getCartVO.setCount(count);
        System.out.println(getCartVO);
    }

}