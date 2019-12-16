package com.szp.mall.inventory.service;

import com.szp.mall.inventory.dataobject.ProductInventory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EhcacheService {
    public static final String CACHE_NAME = "local";

    @Cacheable(value = CACHE_NAME, key = "'key_' + #id")
    public ProductInventory getCacheById(int id) {
        return null;
    }

    @CachePut(value = CACHE_NAME, key = "'key_' + #productInventory.getId()")
    public ProductInventory saveProductInfo(ProductInventory productInventory) {
        return productInventory;
    }
}
