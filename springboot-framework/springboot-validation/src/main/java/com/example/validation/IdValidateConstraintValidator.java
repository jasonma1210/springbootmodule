package com.example.validation;

import com.example.util.IdValidateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdValidateConstraintValidator implements ConstraintValidator<IdValidate,Object> {
    @Override
    public void initialize(IdValidate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * 此方法是对于接受到的o对象进行判断，是否满足于对应的id的操作，如果不满足就提示对应的msg信息
     * @param o
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return IdValidateUtils.isIdNumberValidated(o.toString());
    }
}
