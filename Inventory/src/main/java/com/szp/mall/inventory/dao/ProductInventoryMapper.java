package com.szp.mall.inventory.dao;

import com.szp.mall.inventory.dataobject.ProductInventory;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryMapper {
    public ProductInventory selectById(Integer id);

    public void updateProductInventory(ProductInventory productInventory);

    public void insertProductInventory(ProductInventory productInventory);
}
