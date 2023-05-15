package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AyncService {
    @Async
    public  void doAsyncMethod() {
        try {
            //todo
            Thread.sleep(2000);
            log.debug("异步方法执行了......");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
