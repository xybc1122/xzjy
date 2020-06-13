package com.education.project.exception;


import com.education.project.base.HttpResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * 异常处理控制器
 */
@RestControllerAdvice
public class LsExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "";
        BindingResult result = ex.getBindingResult();
        //组装校验错误信息
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            StringBuffer errorMsgBuffer = new StringBuffer();
            for (ObjectError error : list) {
                if (error instanceof FieldError) {
                    FieldError errorMessage = (FieldError) error;
                    errorMsgBuffer = errorMsgBuffer.append(errorMessage.getDefaultMessage()).append(",");
                }
            }
            //返回信息格式处理
            message = errorMsgBuffer.toString().substring(0, errorMsgBuffer.length() - 1);
        }
        return new ResponseEntity<>(HttpResult.fail(message), headers, status);
    }


    /**
     * 自定义异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public HttpResult Handler(Exception e) {
        if (e instanceof LsException) {
            //自定义异常
            LsException lsException = (LsException) e;
            return HttpResult.fail(lsException.getMsg());
        }
        e.printStackTrace();
        String abbreviate = StringUtils.abbreviate(e.getMessage(), "...", 300);
        return HttpResult.fail("全局异常，未知错误" + abbreviate);
    }
}
