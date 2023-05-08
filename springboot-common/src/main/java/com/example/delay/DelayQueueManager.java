package com.example.delay;

import java.util.concurrent.DelayQueue;

public class DelayQueueManager {

    private static DelayQueue<Worker> delayQueue;

    static {
        // 创建延时队列
        delayQueue = new DelayQueue<>();
    }

    public static DelayQueue<Worker> getDelayQueue(){
        return delayQueue;
    }

    public static void putDelayQueue(Worker worker){
        delayQueue.put(worker);
    }
}