package com.szp.mall.inventory.service;

import com.szp.mall.inventory.dao.ProductInventoryMapper;
import com.szp.mall.inventory.dataobject.ProductInventory;
import com.szp.mall.inventory.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInventoryService {
    @Autowired
    ProductInventoryMapper inventoryDao;
    @Autowired
    private KafkaProducer kafkaProducer;

    public ProductInventory getInventoryById(int id) {
        return inventoryDao.selectById(id);
    }

    public ProductInventory getInventoryCacheById() {
        return null;
    }

    public void updateInventory(int id, int quantity) {
        ProductInventory productInventory = new ProductInventory();
        productInventory.setId(id);
        productInventory.setQuantity(quantity);
        inventoryDao.updateProductInventory(productInventory);
    }

    public void insertInventory(String name, int quantity) {
        ProductInventory productInventory = new ProductInventory();
        productInventory.setName(name);
        productInventory.setQuantity(quantity);
        inventoryDao.insertProductInventory(productInventory);

        //发布更新消息到Kaffa，供缓存更新
        kafkaProducer.publishNewProduct(productInventory);
    }
}
