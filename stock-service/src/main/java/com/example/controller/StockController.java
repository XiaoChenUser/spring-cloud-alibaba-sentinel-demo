package com.example.controller;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.example.vo.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    @RequestMapping("/deduct")
    public CommonResult<String> deduct(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
//        Context context = ContextUtil.getContext();
//        if (context != null) {
//            System.out.println("origin:"+ context.getOrigin());
//        }
        System.out.println("正在扣减库存，product id:" + productId + ",product count:" + count);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CommonResult.data("库存扣减成功");
    }

    @RequestMapping("/get")
    public CommonResult<Integer> getStockById(@RequestParam("productId") Long productId) {
        System.out.println("查询商品 " + productId + " 库存");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CommonResult.data(30);
    }
}
