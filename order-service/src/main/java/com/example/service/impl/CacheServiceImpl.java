package com.example.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.service.CacheService;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {
    @Override
    @SentinelResource(value = "clean-cache")
    public void cleanCache() {
        System.out.println("正在清除 redis 缓存...");
    }
}
