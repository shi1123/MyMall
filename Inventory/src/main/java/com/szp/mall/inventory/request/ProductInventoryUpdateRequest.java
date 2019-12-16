package com.szp.mall.inventory.request;

import com.szp.mall.inventory.dataobject.ProductInventory;
import com.szp.mall.inventory.service.RedisCacheService;
import com.szp.mall.inventory.service.ProductInventoryService;

public class ProductInventoryUpdateRequest implements Request{
    private int productId;
    private ProductInventory productInventory;
    private int quantity;
    private ProductInventoryService dbService;
    private RedisCacheService cacheService;
    public ProductInventoryUpdateRequest(int productId, int quantity, ProductInventoryService dbService, RedisCacheService cacheService){
        this.productId = productId;
        this.quantity = quantity;
        this.dbService = dbService;
        this.cacheService = cacheService;
    }
    @Override
    public void process() {
        //delete from redis
        cacheService.deleteQuantityById(productId);
        //update database
        dbService.updateInventory(productId, quantity);
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
