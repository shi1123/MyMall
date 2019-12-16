package com.szp.mall.inventory.request;

import com.szp.mall.inventory.dataobject.ProductInventory;

public interface Request {
    public void process();

    public int getProductId();

    ProductInventory getProductInventory();
}
