package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class ScheduleTask implements SchedulingConfigurer {
//public class ScheduleTask {
    //先去获得配置文件中对应的默认结果值，每5秒执行一次
    @Value("${majian.cron}")
    private String cron;

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void getTime(){
       log.info(String.valueOf(new Date()));
    }
    @Scheduled(cron = "0 31 15 * * *")
    public void getScheduled01(){
        log.info("我执行了啊！！！！！！！！！");
    }

    /**
     * 通过实现Trigger（触发器）来实现对应的修改
     * 注意修改的是当前出发后的内容，也不是注解里面的那个值！！！！！！！！！！！！
     * @param taskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //触发了对cron值修改的时候，会直接出发该内容，并对其中cron的值进行触发器修改
        taskRegistrar.addTriggerTask(() -> {
            //每次打印的是这个内容：
           log.info("定时任务的结果是："+ LocalDateTime.now());
        }, triggerContext -> {
        //通过触发器实现修改cron表达式,该表达式是通过controller传入，并在下一次执行的时间中修改
            CronTrigger cronTrigger = new CronTrigger(cron);
            return cronTrigger.nextExecutionTime(triggerContext);
        });
    }
}
