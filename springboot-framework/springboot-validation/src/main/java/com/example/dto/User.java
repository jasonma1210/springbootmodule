package com.example.dto;

import com.example.validation.IdValidate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class User implements Serializable {
    @NotNull(message = "当前用户的id必须定义")
    private Integer id;
    @NotNull(message = "用户名不能为空")
    @Length(min=8,max=20,message = "当前用户名长度必须大于8位，小于20位")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "用户昵称限制：最多20字符，包含文字、字母和数字")
    private String name;
    @NotNull(message = "性别必须输入，难道你跨越了性别")
    private String gender;
    @NotNull(message = "密码不能为空")
    @Length(min=8,max=20,message = "当前密码长度必须大于8位，小于20位")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "密码限制：最多20字符，包含文字、字母和数字")
    private String pass;
    @NotNull(message = "邮箱必须输入")
    @Email(message = "邮箱格式有误")
    private String email;
    //身份证号码,在中国身份证包括15和18位，都是按照地区，出生日期+4位随机来实现
    @IdValidate
    private String uid;
}
