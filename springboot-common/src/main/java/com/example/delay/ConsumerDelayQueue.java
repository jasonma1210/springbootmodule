package com.example.delay;

import java.util.concurrent.DelayQueue;

public class ConsumerDelayQueue implements Runnable {
    // 延时队列 ,消费者从其中获取消息进行消费
    private DelayQueue<Worker> queue;

    public ConsumerDelayQueue(DelayQueue<Worker> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Worker take = queue.take();
                System.out.println("消费消息类型：" + take.getType() + " 消息体：" + take.getBody()+":"+System.currentTimeMillis());
                //TODO 此处可以进行推送等一系列操作
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}