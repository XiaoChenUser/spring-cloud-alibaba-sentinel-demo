package com.example.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.example.enumeration.ExceptionEnum;
import com.example.exception.ServiceException;
import com.example.feign.StockFeignService;
import com.example.service.CacheService;
import com.example.service.OrderService;
import com.example.vo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.UndeclaredThrowableException;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final StockFeignService stockService;
    private final OrderService orderService;
    private final CacheService cacheService;

    @Autowired
    public OrderController(@Qualifier("stockFeignService") StockFeignService stockService, OrderService orderService, CacheService cacheService) {
        this.stockService = stockService;
        this.orderService = orderService;
        this.cacheService = cacheService;
    }

    @RequestMapping("/add")
    public CommonResult<String> addOrder(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
//        try {
        CommonResult stockResult = stockService.deduct(productId, count);
        if (stockResult != null) {
            System.out.println(JSON.toJSONString(stockResult));
            if (stockResult.getCode() != CommonResult.SUCCESS_CODE) {
                throw new ServiceException(ExceptionEnum.SYS_ERROR, "库存服务调用出现异常，请稍后重试");
            }
        }
        orderService.store();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        } catch (UndeclaredThrowableException exception) {
//            /**
//             * 调用链路因 sentinel 流控、熔断等出现 BlockException 时，会被包装成 java.lang.reflect.UndeclaredThrowableException
//             * 异常抛出，可以通过 getCause() 获取诱因 BlockException。
//             */
//            Throwable cause = exception.getCause();
//            if (cause != null && cause instanceof BlockException) {
//                BlockException blockException = (BlockException) cause;
//                System.out.println(blockException.getRule().getResource() + ":" + blockException.getRuleLimitApp());
//            }
//        }
        return CommonResult.data("新增订单成功");
    }

    @GetMapping("/search")
    @SentinelResource(value = "order-search", blockHandler = "searchBlockHandler")
    public CommonResult<String> search(Long id) {
        System.out.println("查询订单");
//        int a = 10/0;
        return CommonResult.data("订单 " + id);
    }

    public CommonResult<String> searchBlockHandler(Long id, BlockException e) {
        return CommonResult.error(ExceptionEnum.BLOCK_EXCEPTION);
    }

    @GetMapping("/pay")
    public CommonResult<String> pay(Long id) {
        System.out.println("订单支付中...");
        orderService.store();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CommonResult.data("订单 " + id + " 支付成功");
    }

    @GetMapping("/clean")
    public CommonResult<String> cleanCache() {
        cacheService.cleanCache();
        return CommonResult.data("缓存清除成功");
    }
}
