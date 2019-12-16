package com.szp.mall.inventory.request;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//@Component
public class RequestQueue {
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();
    private Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>();
    public void addQueue(ArrayBlockingQueue arrayBlockingQueue){
        queues.add(arrayBlockingQueue);
    }

    public int getQueueSize(){
        return  queues.size();
    }

    public ArrayBlockingQueue<Request> getQueue(int index) {
        return queues.get(index);
    }
    public Map getFlagMap(){
        return flagMap;
    }
}
