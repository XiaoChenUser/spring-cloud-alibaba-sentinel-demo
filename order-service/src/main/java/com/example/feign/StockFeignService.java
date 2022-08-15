package com.example.feign;

import com.example.feign.fallback.StockServiceFallback;
import com.example.vo.CommonResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Qualifier("stockFeignService")
//@FeignClient(name = "stock-service", path = "/stock")
@FeignClient(name = "stock-service", path = "/stock", fallback = StockServiceFallback.class)
public interface StockFeignService {
    @RequestMapping("deduct")
    CommonResult<String> deduct(@RequestParam("productId") Long productId, @RequestParam("count")Integer count);
}
