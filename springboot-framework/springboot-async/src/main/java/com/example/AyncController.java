package com.example;

import com.example.service.AyncService;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AyncController {


    @Autowired
    private AyncService ayncService;

    @GetMapping("test")
    public String test(){
            ayncService.doAsyncMethod();
            ayncService.doAsyncMethod();
//        doAsyncMethod();
//        doAsyncMethod();
        log.debug("主线程执行结束......");
        return "SUCCESS";
    }


//    @Async
//     void doAsyncMethod() {
//        try {
//            //todo
//            Thread.sleep(2000);
//            log.debug("异步方法执行了......");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

}
