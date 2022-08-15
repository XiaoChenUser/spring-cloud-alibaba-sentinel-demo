package com.example.exception;

import com.example.enumeration.ExceptionEnum;

public class ServiceException extends RuntimeException {
    private final Integer code;

    public ServiceException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }

    public ServiceException(ExceptionEnum exceptionEnum, String errorMsg) {
        super(errorMsg);
        this.code = exceptionEnum.getCode();
    }

    public ServiceException(ExceptionEnum exceptionEnum, Throwable cause) {
        super(cause);
        this.code = exceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
