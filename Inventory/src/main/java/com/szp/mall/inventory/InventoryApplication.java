package com.szp.mall.inventory;

import com.szp.mall.inventory.kafka.KafkaMessage;
import com.szp.mall.inventory.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class InventoryApplication {
    @Autowired
    private KafkaProducer kafkaSender;

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    //然后每隔1分钟执行一次
//    @Scheduled(fixedRate = 100 * 60)
    public void testKafka() throws Exception {
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setId(1);
        kafkaMessage.setName("jone");
        kafkaMessage.setPassword("123456");
        kafkaSender.sendKafkaMessage(kafkaMessage);
    }
}
