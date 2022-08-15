package com.example.handler;

import com.example.enumeration.ExceptionEnum;
import com.example.exception.ServiceException;
import com.example.vo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ResponseBody
@ControllerAdvice(basePackages = "com.example.controller")
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<CommonResult> {
    private static final Logger logger = LoggerFactory.getLogger(GlobalResponseBodyHandler.class);


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public CommonResult beforeBodyWrite(CommonResult body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body!=null && body.getCode() != CommonResult.SUCCESS_CODE) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return body;
    }


    @ExceptionHandler(value = {ServiceException.class})
    public CommonResult serviceExceptionHandler(HttpServletRequest request, HttpServletResponse response,ServiceException exception) {
        logger.warn("[ServiceException] - {}", exception.getMessage());
        exception.printStackTrace();

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return CommonResult.error(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResult missingServletRequestParameterExceptionHandler(HttpServletRequest request, HttpServletResponse response, MissingServletRequestParameterException ex) {
        logger.warn("[MissingServletRequestParameterException] - {}", ex.getMessage());
        ex.printStackTrace();

        //设置响应状态码，让浏览器和测试工具（Jmeter）识别这是错误响应，方便观察统计“流控、熔断”等操作的结果
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        // 包装 CommonResult 结果
        return CommonResult.error(ExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getCode(),
                ExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.warn("[Exception] - {}", e.getMessage());
        e.printStackTrace();

        //设置响应状态码，让浏览器和测试工具（Jmeter）识别这是错误响应，方便观察统计“流控、熔断”等操作的结果
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return CommonResult.error(ExceptionEnum.SYS_ERROR.getCode(),ExceptionEnum.SYS_ERROR.getMessage());
    }
}
