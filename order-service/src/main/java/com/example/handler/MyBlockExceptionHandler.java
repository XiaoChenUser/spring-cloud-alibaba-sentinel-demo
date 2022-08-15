package com.example.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson2.JSON;
import com.example.enumeration.ExceptionEnum;
import com.example.vo.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        log.info(JSON.toJSONString(e.getRule()));
        CommonResult<String> commonResult = null;
        if (e instanceof FlowException) {
            commonResult = CommonResult.error(ExceptionEnum.FLOW_EXCEPTION.getCode(), "被流控了");
        } else if (e instanceof DegradeException) {
            commonResult = CommonResult.error(ExceptionEnum.DEGRADE_EXCEPTION.getCode(), "被降级了");
        } else if (e instanceof ParamFlowException) {
            commonResult = CommonResult.error(ExceptionEnum.BLOCK_EXCEPTION.getCode(), "热点参数");
        } else if (e instanceof AuthorityException) {
            commonResult = CommonResult.error(ExceptionEnum.BLOCK_EXCEPTION.getCode(), "访问权限控制");
        } else if (e instanceof SystemBlockException) {
            commonResult = CommonResult.error(ExceptionEnum.BLOCK_EXCEPTION.getCode(), "系统自适应限流");
        } else {
            commonResult = CommonResult.error(ExceptionEnum.BLOCK_EXCEPTION.getCode(), "未知限流");
        }

        response.setStatus(500);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), commonResult);
    }
}
