package com.szp.mall.inventory.service.impl;

import com.szp.mall.inventory.dataobject.ProductInventory;
import com.szp.mall.inventory.request.Request;
import com.szp.mall.inventory.request.RequestQueue;
import com.szp.mall.inventory.service.RequestAsyncProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
@Service
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {
    @Autowired
    RequestQueue requestQueue;
    @Override
    public void process(Request request) {
        int pt = request.getProductId();
        getRouteQueue(pt).add(request);
    }
    private ArrayBlockingQueue<Request> getRouteQueue(Integer productId){
        String key = String.valueOf(productId);

        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

        int index = (requestQueue.getQueueSize() -1) & hash;
        return requestQueue.getQueue(index);
    }

}
