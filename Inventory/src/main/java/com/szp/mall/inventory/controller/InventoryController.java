package com.szp.mall.inventory.controller;

import com.szp.mall.inventory.dataobject.ProductInventory;
import com.szp.mall.inventory.request.ProductInventoryCheckRequest;
import com.szp.mall.inventory.request.ProductInventoryUpdateRequest;
import com.szp.mall.inventory.request.Request;
import com.szp.mall.inventory.service.EhcacheService;
import com.szp.mall.inventory.service.RedisCacheService;
import com.szp.mall.inventory.service.ProductInventoryService;
import com.szp.mall.inventory.service.impl.RequestAsyncProcessServiceImpl;
import com.szp.mall.inventory.vo.MyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {
    @Autowired
    ProductInventoryService inventoryService;
    @Autowired
    RedisCacheService redisCacheService;
    @Autowired
    RequestAsyncProcessServiceImpl requestAsyncProcessService;
    @Autowired
    EhcacheService ehcacheService;

    Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @RequestMapping("/updateProductById")
    public MyResponse updateProductById(int productId, int quantity) {
        ProductInventory productInventory = null;
        try {

            Request request = new ProductInventoryUpdateRequest(productId, quantity, inventoryService, redisCacheService);
            requestAsyncProcessService.process(request);
            MyResponse myResponse = new MyResponse(MyResponse.SUCCESS);
            return myResponse;
        } catch (Exception e) {
            return new MyResponse(MyResponse.FAILURE, e.getMessage());
        }
//        return inventoryService.getInventoryById(productId);
    }

    @RequestMapping("/getInventoryById")
    public MyResponse getInventoryByProductId(int productId) {
        Request request = new ProductInventoryCheckRequest(productId, inventoryService, redisCacheService);
        try {
            requestAsyncProcessService.process(request);
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;
            while (true) {
                Integer quantity = redisCacheService.getProductFromRedis(productId);
                if (null != quantity) {
                    return new MyResponse(quantity);
                } else {
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;

                    if (waitTime > 2000) {//从缓存中获取超时，直接从数据库中查询
                        logger.info("从缓存中获取超时，直接从数据库中查询!");
                        MyResponse myResponse = new MyResponse(inventoryService.getInventoryById(productId));
                        return myResponse;
                    }
                }
            }
        } catch (Exception e) {
            return new MyResponse(MyResponse.FAILURE, e.getMessage());
        }
//        return inventoryService.getInventoryById(productId);
    }
    @RequestMapping("/getEhcacheById")
    public MyResponse getInventoryFromEhcache(int productId){
        return new MyResponse(ehcacheService.getCacheById(productId));
    }
    @RequestMapping("/saveInventoryEhcache")
    public MyResponse saveInventoryEhcache(ProductInventory productInventory){

        return new MyResponse(ehcacheService.saveProductInfo(productInventory));
    }
    @RequestMapping("/insertInventoty")
    public MyResponse insertInventory(String name, int quantity){
        inventoryService.insertInventory(name, quantity);
        return new MyResponse();
    }
}
