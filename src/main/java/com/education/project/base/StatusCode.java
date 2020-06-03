package com.education.project.base;

public enum StatusCode implements BaseEnum {

    SUCCESS(200, "操作成功"),
    FAILURE(400, "操作失败"),
    UN_AUTHORIZED(401, "请求未授权"),
    ACCESS_TOKEN_INVALID(403, "token已经失效!"),
    NOT_FOUND(404, "未知请求"),
    MSG_NOT_READABLE(400, "消息不能读取"),
    METHOD_NOT_SUPPORTED(405, "不支持当前请求方法"),
    MEDIA_TYPE_NOT_SUPPORTED(415, "不支持当前媒体类型"),
    REQ_REJECT(403, "请求被拒绝"),
    INTERNAL_SERVER_ERROR(500, "服务器异常"),
    PARAM_MISS(400, "缺少必要的请求参数"),
    PARAM_TYPE_ERROR(400, "请求参数类型错误"),
    PARAM_BIND_ERROR(400, "请求参数绑定错误"),
    PARAM_VALID_ERROR(400, "参数校验失败"),
    ERR_NULL_POINT(10000, "空指针异常"),
    ERR_NOT_IMPLEMENTS(20000, "接口未实现"),
    ERR_ADD(10001, "新增异常"),
    ERR_DELETE(10002, "删除异常"),
    ERR_UPDATE(10003, "更新异常");

    private final Integer code;
    private final String message;

    StatusCode(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
