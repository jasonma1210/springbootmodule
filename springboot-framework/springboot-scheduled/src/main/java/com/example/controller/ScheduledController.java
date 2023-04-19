package com.example.controller;

import com.example.ScheduleTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ScheduledController {

    @Resource
    private ScheduleTask scheduleTask;

    @GetMapping("/success")
    public String success(String cron){
            scheduleTask.setCron(cron);
            return "success";
    }
}
