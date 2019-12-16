package com.szp.mall.inventory.request;

import com.szp.mall.inventory.dataobject.ProductInventory;
import com.szp.mall.inventory.service.RedisCacheService;
import com.szp.mall.inventory.service.ProductInventoryService;

public class ProductInventoryCheckRequest implements Request {
    private ProductInventory productInventory;
    private Integer quantity;
    private int productId;
    private ProductInventoryService dbService;
    private RedisCacheService cacheService;
    public ProductInventoryCheckRequest(int productId, ProductInventoryService dbService, RedisCacheService cacheService) {
        this.productId = productId;
        this.dbService = dbService;
        this.cacheService = cacheService;
    }

    @Override
    public void process() {
        //1 check the cache
        Integer quantity = cacheService.getProductFromRedis(productId);
        if(null == quantity){
            //2 get data from database
            productInventory = dbService.getInventoryById(productId);
            if (null != productInventory){
                this.quantity = productInventory.getQuantity();
//                update cache;
                cacheService.updateProductCache(productId, this.quantity);
            }
        }else {
            this.quantity = quantity;
        }
    }

    @Override
    public int getProductId() {
        return productId;
    }

    @Override
    public ProductInventory getProductInventory() {
        return productInventory;
    }
}
