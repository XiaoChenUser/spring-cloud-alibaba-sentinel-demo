package com.example.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.service.CacheService;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final CacheService cacheService;

    @Autowired
    public OrderServiceImpl(CacheService cacheService) {
        this.cacheService = cacheService;
    }


    @Override
    @SentinelResource(value = "store-order")
    public void store() {
        System.out.println("store into db...");
        cacheService.cleanCache();
    }
}
