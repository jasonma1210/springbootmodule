package com.example.controller;

import com.example.dto.TUser;
import com.example.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProdController {
    private final static String TOPIC_NAME = "test01";
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;
    @PostMapping("/send")
    public void send(@RequestBody TUser tUser) throws JsonProcessingException {
        kafkaTemplate.send(TOPIC_NAME, 0 , tUser.getId().toString(), JsonUtil.toJson(tUser));
    }
}