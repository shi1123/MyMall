package com.szp.mall.inventory.config;

import com.szp.mall.inventory.request.RequestQueue;
import com.szp.mall.inventory.threadpool.RequestProcessorThreadPool;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

@SpringBootConfiguration
public class RequestProcessConfig {
    @Bean
    public RequestQueue requestQueue(){
        return new RequestQueue();
    }
    @Bean
    @DependsOn("requestQueue")
    public RequestProcessorThreadPool requestProcessorThreadPool(RequestQueue requestQueue){
        RequestProcessorThreadPool requestProcessorThreadPool = new RequestProcessorThreadPool(requestQueue);
        requestProcessorThreadPool.setRequestQueue(requestQueue);
        return requestProcessorThreadPool;
    }
}
