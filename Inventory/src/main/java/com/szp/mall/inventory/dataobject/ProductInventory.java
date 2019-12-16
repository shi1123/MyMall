package com.szp.mall.inventory.dataobject;

import java.io.Serializable;

public class ProductInventory implements Serializable {
    private static final long serialVersionUID = -6903020569586856015L;
    private int id;
    private String name;
    private int quantity;

    @Override
    public String toString() {
        return "ProductInventory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
