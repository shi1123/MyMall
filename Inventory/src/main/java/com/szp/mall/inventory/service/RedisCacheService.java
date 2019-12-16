package com.szp.mall.inventory.service;

import com.szp.mall.inventory.dataobject.ProductInventory;
import com.szp.mall.inventory.dataobject.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class RedisCacheService {
    private final String productQuantityRedisKey = "product:quantity:id:";
    @Autowired
    private RedisTemplate<String, Object> redisTemplateInteger;

    public void updateProductCache(int id, Integer quantity){
        String key = productQuantityRedisKey + id;
        redisTemplateInteger.opsForValue().set(key, quantity);
    }

    public Integer getProductFromRedis(int id){
        String key = productQuantityRedisKey + id;
       return (Integer)redisTemplateInteger.opsForValue().get(key);
    }

    public void deleteQuantityById(int productId) {
        String key = productQuantityRedisKey + productId;
        redisTemplateInteger.delete(key);
    }
}
