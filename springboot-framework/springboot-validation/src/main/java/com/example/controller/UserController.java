package com.example.controller;

import com.example.dto.User;
import com.example.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    /**
     * 在传入的参数前面加上@Validated 告知那个属性类需要进行校验
     * @param user  传入要检验的参数
     * @param  bindingResult     当校验失败的时候，把校验结果取出 取出message
     * @return
     */
    @PostMapping("/register")
    public ResultVO register(@Validated @RequestBody User user, BindingResult bindingResult){
        ResultVO rv;
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError f : fieldErrors){
                log.error(f.getField()+":"+f.getDefaultMessage());
            }
            System.out.println("------------------------------------------------");
            log.error(bindingResult.getFieldErrors().toString());
                rv = ResultVO.failure("500","当前格式不规范，请重新定义", fieldErrors);
        }else{
            rv = ResultVO.success("200","注册成功");
        }
        return rv;
    }
}
