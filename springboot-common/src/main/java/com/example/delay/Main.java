package com.example.delay;

import com.example.dto.Order;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列实现
 */
public class Main {
    static Long time = 5000L;
    public static void main(String[] args) throws InterruptedException {

        Order order1 = new Order();
        order1.setOrderId("1");
        order1.setOrderName("iphone14promax");
        Worker<Order> orderWorker1 = new Worker<>(1, order1, time);
        DelayQueueManager.putDelayQueue(orderWorker1);
        Order order2 = new Order();
        order2.setOrderId("2");
        order2.setOrderName("iphone14pro");
        Worker<Order> orderWorker2 = new Worker<>(2, order2, time+5000L);
        DelayQueueManager.putDelayQueue(orderWorker2);

        //线程池实现

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 32, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.execute(new ConsumerDelayQueue(DelayQueueManager.getDelayQueue()));
        threadPoolExecutor.execute(new ConsumerDelayQueue(DelayQueueManager.getDelayQueue()));

        threadPoolExecutor.shutdown();
    }
}
