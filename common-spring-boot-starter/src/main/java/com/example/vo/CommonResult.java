package com.example.vo;

import com.example.enumeration.ExceptionEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommonResult<T> {
    public static final int SUCCESS_CODE = 200;

    private int code;
    private String msg;
    private T data;


    private CommonResult() {
    }

    public static <T> CommonResult<T> data(T data) {
        return new CommonResult<T>().setCode(SUCCESS_CODE).setData(data);
    }

    public static<T> CommonResult<T> error(int code, String msg) {
        return new CommonResult<T>().setCode(code).setMsg(msg);
    }

    public static<T> CommonResult<T> error(ExceptionEnum exceptionEnum) {
        return new CommonResult<T>().setCode(exceptionEnum.getCode()).setMsg(exceptionEnum.getMessage());
    }

    public static<T> CommonResult<T> error(CommonResult<T> commonResult) {
        return new CommonResult<T>().setCode(commonResult.getCode()).setMsg(commonResult.getMsg());
    }
}
