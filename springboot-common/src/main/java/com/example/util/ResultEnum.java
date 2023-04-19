package com.example.util;

/**
 *
 * @author jianma
 */
public enum ResultEnum {
    /**
     * 所有的配置信息
     */
     SELECTCLASSSTUDENTALL_SUCESS("100001","查询所有的学生和班级信息成功"),
     CREATECLASSSTUDENT_SUCESS("100002","插入学生信息成功"),

    SELECT_REDIS_MEITUANINFO_SUCCESS("100003","查询美团用户数据成功"),
    ADD_REDIS_MEITUANINFO_SUCCESS("100004","添加美团用户数据成功"),

    SELECT_REDIS_MEITUANINFO_FAILURE("200003","查询美团用户数据失败"),
    CREATECLASSSTUDENT_FAILURE("200002","插入学生信息失败"),

    /* 成功 */
    SUCCESS("200", "成功"),

    /* 登出成功 */
    LOGOUT("201", "登出成功"),
    /* 默认失败 */
    COMMON_FAIL("999", "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID("1001", "参数无效"),
    PARAM_IS_BLANK("1002", "参数为空"),
    PARAM_TYPE_ERROR("1003", "参数类型错误"),
    PARAM_NOT_COMPLETE("1004", "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN("2001", "用户未登录"),
    USER_ACCOUNT_EXPIRED("2002", "账号已过期"),
    USER_CREDENTIALS_ERROR("2003", "密码错误"),
    USER_CREDENTIALS_EXPIRED("2004", "密码过期"),
    USER_ACCOUNT_DISABLE("2005", "账号不可用"),
    USER_ACCOUNT_LOCKED("2006", "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST("2007", "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST("2008", "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS("2009", "您的账号在别处登录了，你被迫下线"),

    /* 业务错误 */
    NO_PERMISSION("3001", "没有权限"),

    /* 异常处理 */
    EXCEPTION_NULLPOINT("4001","空指针异常"),
    EXCEPTION_ARITHMETIC("4002","数学异常"),
    EXCEPTION_BUSINESS("4003","业务异常");



    private final String resCode;
    /** 每次返回后得到的信息是什么 */
    private  final String resMsg;

    public String getResCode() {
        return resCode;
    }
    ResultEnum(String resCode, String resMsg){
            this.resCode = resCode;
            this.resMsg =resMsg;
    }

    public String getResMsg() {
        return resMsg;
    }

}
