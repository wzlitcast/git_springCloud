package com.it.cast;

import com.aspire.commons.ConstantsCodeCommon;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.aspire.commons.AjaxResult;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Set;

/**
 * @author: Oliver.li
 * @Description: 全局异常处理
 * @date: 2018/5/21 15:19
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResult<ObjectNode> handleException(Exception e){
        logger.error(e.getMessage(), e);

        //Hibernate Validator验证消息返回
        BindingResult result = null;
        if (e instanceof MethodArgumentNotValidException){
            result = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException){
            result = ((BindException) e).getBindingResult();
        } else if (e instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<?> violation : constraintViolations) {
                errorMsg.append(violation.getMessage()).append(",");
            }
            errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
            return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.VERIFICATION_FAILED.getCode(),errorMsg.toString());
        }
        else if (e instanceof FeignException){
            //进入feign异常
            if(((FeignException) e).status()==(HttpStatus.METHOD_NOT_ALLOWED.value())){
                return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.METHOD_NOT_ALLOWED);
            }else if (((FeignException) e).status()==HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.METHOD_NOT_ALLOWED);
            }else if (((FeignException) e).status()==HttpStatus.NOT_FOUND.value()){
                return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.DATA_IS_EMPTY);
            }else if(((FeignException) e).status()==HttpStatus.BAD_REQUEST.value()){
                return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.PARAMETER_IS_INVALID_ERROR);
            }else if(((FeignException) e).status()==HttpStatus.FORBIDDEN.value()){
                return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.PARAMETER_IS_INVALID_ERROR);
            }
        }else if (e instanceof IOException){
            return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.JSON_PARSING_EXCEPTION);
        }else if(e instanceof HystrixRuntimeException){
            return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.SERVICE_TIME_OUT);
        }else if(e instanceof HttpMessageNotReadableException){
            return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.PARAMETER_IS_INVALID_ERROR);
        }else if(e instanceof HttpRequestMethodNotSupportedException){
            return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.REQUEST_TYPES_EXCEPTION.getCode(),e.getMessage());
        }else if(e instanceof MissingServletRequestParameterException){
            return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.PARAMETER_IS_EMPTY);
        }else if(e instanceof HttpMediaTypeNotSupportedException){
            return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.REQUEST_PARAMETER_FORMAT_EXCEPTION);
        }
        if (result != null) {
            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                errorMsg.append(error.getDefaultMessage()).append(",");
            }
            errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
            return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.VERIFICATION_FAILED.getCode(),errorMsg.toString());
        }
        return new AjaxResult<ObjectNode>().fail(ConstantsCodeCommon.SERVER_EXCEPTION);
    }
}

