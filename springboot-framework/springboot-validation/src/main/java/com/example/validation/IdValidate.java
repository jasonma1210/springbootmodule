package com.example.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
//把校验的操作绑定到该注解上
@Constraint(validatedBy = IdValidateConstraintValidator.class)
public @interface IdValidate {
    String message() default "当前身份证验证有误，请重试";
    //groups payload必须都存在，否则无法实现
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
