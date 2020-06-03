package com.education.project.base;

import java.io.Serializable;

public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = -6411426798186281331L;

    private int code;

    private boolean success;

    private T data;

    private String message;

    //成功
    public static HttpResult success() {
        return new HttpResult();
    }

    //成功  入参msg
    public static HttpResult success(String msg) {
        return new HttpResult(msg, true);
    }

    //成功  入参 msg data
    public static <T> HttpResult<T> success(T data, String msg) {
        return new HttpResult(data, msg);
    }

    //成功  入参  data
    public static <T> HttpResult<T> success(T data) {
        return new HttpResult(data);
    }

    //成功 入参枚举
    public static <T> HttpResult<T> success(BaseEnum baseEnum) {

        return new HttpResult(baseEnum, true);
    }

    //成功 入参枚举 跟data
    public static <T> HttpResult<T> success(T data, BaseEnum baseEnum) {

        return new HttpResult(data, baseEnum, true);
    }


    //失败 返回msg
    public static  <T> HttpResult<T> fail(String msg) {
        return new HttpResult(-1, false, msg);
    }

    private HttpResult(T data, BaseEnum baseEnum, boolean isSuccess) {
        this.data = data;
        this.code = baseEnum.getCode();
        this.success = isSuccess;
        this.message = baseEnum.getMessage();
    }

    private HttpResult(BaseEnum baseEnum, boolean isSuccess) {
        this.code = baseEnum.getCode();
        this.success = isSuccess;
        this.message = baseEnum.getMessage();
    }

    private HttpResult(T data, String msg) {
        this.code = StatusCode.SUCCESS.getCode();
        this.data = data;
        this.message = msg;
        this.success = true;
    }

    private HttpResult(T data) {
        this.code = StatusCode.SUCCESS.getCode();
        this.data = data;
        this.message = StatusCode.SUCCESS.getMessage();
        this.success = true;
    }

    private HttpResult() {
        this.code = StatusCode.SUCCESS.getCode();
        this.message = StatusCode.SUCCESS.getMessage();
        this.success = true;
    }

    private HttpResult(int code, boolean isSuccess, String msg) {
        this.code = code;
        this.success = isSuccess;
        this.message = msg;
    }

    private HttpResult(String msg, boolean isSuccess) {
        this.code = StatusCode.SUCCESS.getCode();
        this.success = isSuccess;
        this.message = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
