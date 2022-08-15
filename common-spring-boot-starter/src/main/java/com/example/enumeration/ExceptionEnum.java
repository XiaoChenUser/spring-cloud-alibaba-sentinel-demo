package com.example.enumeration;


public enum ExceptionEnum {
    SUCCESS(0, "成功"),
    SYS_ERROR(1000, "服务端发生异常"),
    BLOCK_EXCEPTION(1001, "服务阻塞，请稍后再试"),
    FLOW_EXCEPTION(1002, "服务繁忙，请稍后再试"),
    DEGRADE_EXCEPTION(1003, "服务异常，请稍后再试"),
    MISSING_REQUEST_PARAM_ERROR(2001, "参数缺失");

    private int code;
    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
