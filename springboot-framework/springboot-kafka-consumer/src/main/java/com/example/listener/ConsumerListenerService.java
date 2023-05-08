package com.example.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class ConsumerListenerService {
    @KafkaListener(topics = "test01",groupId = "MyGroup1")
    public void listenGroup01(ConsumerRecord<String, String> record, Acknowledgment ack) {
        System.out.println("-----------------MyGroup1 start-------------------");
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
        //手动提交offset
        ack.acknowledge();
        System.out.println("-----------------MyGroup1 end-------------------");
    }
    @KafkaListener(topics = "test01",groupId = "MyGroup2")
    public void listenGroup02(ConsumerRecord<String, String> record, Acknowledgment ack) {
        System.out.println("-----------------MyGroup2 started-------------------");
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
        //手动提交offset
        ack.acknowledge();
        System.out.println("-----------------MyGroup2 end-------------------");
    }
}
