package com.szp.mall.inventory.threadpool;


import com.szp.mall.inventory.request.Request;
import com.szp.mall.inventory.request.RequestQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Component
public class RequestProcessorThreadPool {
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @Autowired
    RequestQueue requestQueue;

    public RequestProcessorThreadPool(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
        for(int i = 0; i < 10; i++){
            ArrayBlockingQueue<Request> arrayBlockingQueue = new ArrayBlockingQueue<>(100);
            requestQueue.addQueue(arrayBlockingQueue);
            threadPool.submit(new WorkThread(arrayBlockingQueue, requestQueue));
        }
    }

    public void setRequestQueue(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
    }
}
