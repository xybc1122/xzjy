package com.education.project.exception;


import com.education.project.base.HttpResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理控制器
 */
@RestControllerAdvice
public class LsExceptionHandler {

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
