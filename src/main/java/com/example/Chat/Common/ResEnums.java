package com.example.Chat.Common;

public enum ResEnums {
    // 成功
    SUCCESS(200, "ok"),
    // 失败
    FAIL(500, "fail"),
    // 未授权
    UNAUTHORIZED(401, "unauthorized"),
    // 未找到
    NOT_FOUND(404, "not found"),
    // 未知错误
    BAD_REQUEST(400, "bad request"),
    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500, "internal server error"),
    // 参数错误
    PARAMETER_ERROR(400, "parameter error"),
    // 业务异常
    BUSINESS_EXCEPTION(1000, "business exception");

    private final int code;
    private final String msg;

    ResEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
