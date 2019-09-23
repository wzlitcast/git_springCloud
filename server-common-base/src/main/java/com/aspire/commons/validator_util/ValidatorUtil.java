package com.aspire.commons.validator_util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 统一异常管理配置
 */
public class ValidatorUtil {
   static Validator validator =Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T t,Class<?>... groups){
        Set<ConstraintViolation<T>> validate = validator.validate(t,groups);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        }
    }


}
