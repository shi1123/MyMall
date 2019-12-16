package com.szp.mall.inventory.threadpool;

import com.szp.mall.inventory.request.ProductInventoryCheckRequest;
import com.szp.mall.inventory.request.ProductInventoryUpdateRequest;
import com.szp.mall.inventory.request.Request;
import com.szp.mall.inventory.request.RequestQueue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class WorkThread implements Callable<Boolean> {
    private ArrayBlockingQueue<Request> arrayBlockingQueue;
    private RequestQueue requestQueue;

    public WorkThread(ArrayBlockingQueue abq, RequestQueue requestQueue) {
        this.arrayBlockingQueue = abq;
        this.requestQueue = requestQueue;
    }

    @Override
    public Boolean call() throws Exception {

        while (true) {
            try {
                System.out.println("进入循环等待请求");
                Request request = arrayBlockingQueue.take();
                Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();
                int productId = request.getProductId();
                if (request instanceof ProductInventoryUpdateRequest) {
                    flagMap.put(productId, true);
                } else if (request instanceof ProductInventoryCheckRequest) {
                    Boolean flag = flagMap.get(productId);

                    if (flag == null) {//队列中无更新和查询请求，将自己放入队列中，并做标记
                        flagMap.put(productId, false);
                    } else if (flag) {//队列中有更新请求，将自己放入队列中，并做标记
                        flagMap.put(productId, false);
                    } else {//队列中有查询请求，将该请求丢弃
//                        return true;
                        continue;
                    }
                }
                request.process();
                flagMap.remove(productId);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            return true;
        }

//        return true;
    }
}
