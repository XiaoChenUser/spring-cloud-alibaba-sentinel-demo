package com.example.feign.fallback;

import com.example.enumeration.ExceptionEnum;
import com.example.feign.StockFeignService;
import com.example.vo.CommonResult;
import org.springframework.stereotype.Component;

@Component
public class StockServiceFallback implements StockFeignService {
    @Override
    public CommonResult<String> deduct(Long productId, Integer count) {
        return CommonResult.error(ExceptionEnum.BLOCK_EXCEPTION.getCode(), "订单扣减库存失败");
    }
}
