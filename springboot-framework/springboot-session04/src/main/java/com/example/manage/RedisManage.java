package com.example.manage;

public interface RedisManage {
    //key的第一个前缀的名称
    public static final String PREFIX = "example:";
    //key中间的名称
    public static final String MIDDLE_LOGIN = "login:";

    //key最后的内容涉及到全部查询
    public static final String LAST_ALL = "all";

    /**
     * 添加登录token
     * @param id
     * @param value
     * @param TTL
     */
    void addLogin(Integer id,String value,Long TTL);

    Object checkLoginUser(Integer id);

    void delUser(Integer id);

    void add2Token(String key,String value,Long TTL1,Long TTL2);
        }
