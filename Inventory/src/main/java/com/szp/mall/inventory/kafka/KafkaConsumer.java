package com.szp.mall.inventory.kafka;

import com.google.gson.Gson;
import com.szp.mall.inventory.dataobject.ProductInventory;
import com.szp.mall.inventory.service.EhcacheService;
import com.szp.mall.inventory.service.RedisCacheService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private Gson gson = new Gson();
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private EhcacheService ehcacheService;
    @KafkaListener(topics="myTopic", groupId = "myGroup")
    public void obtainMessage(ConsumerRecord<String, String> consumerRecord){
        System.out.println("message recievec!");
        String topic = consumerRecord.topic();
        String key = consumerRecord.key();
        String value = consumerRecord.value();
        int partition = consumerRecord.partition();
        long timeStamp = consumerRecord.timestamp();

        System.out.println(" topic" + topic);
        System.out.println("key " + key);
        System.out.println("value " + value);
        System.out.println("partition " + partition);
        System.out.println("timeStamp " + timeStamp);
        System.out.println(" ");
    }

    @KafkaListener(topics="newProduct", groupId = "productGroup")
    public void obtainProductMessage(ConsumerRecord<String, String> consumerRecord){
        String topic = consumerRecord.topic();
        String key = consumerRecord.key();
        String value = consumerRecord.value();
        int partition = consumerRecord.partition();
        long timeStamp = consumerRecord.timestamp();

        logger.info("收到新产品更新消息，开始刷新缓存，topic:{},key:{},value:{},partition:{},timestap:{}",
                topic, key, value, partition, timeStamp );

        ProductInventory productInventory = gson.fromJson(value, ProductInventory.class);
        ehcacheService.saveProductInfo(productInventory);
        redisCacheService.updateProductCache(productInventory.getId(), productInventory.getQuantity());

    }
}
