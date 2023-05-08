package com.example.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Worker<T> implements Delayed {
    //消息类型
    private int type;
    // 消息内容
    private T body;
    //创建时刻的时间
    private long start = System.currentTimeMillis();
    //延迟时长，这个是必须的属性因为要按照这个判断延时时长。
    private long excuteTime;

    // 延迟任务是否到时就是按照这个方法判断如果返回的是负数则说明到期
    // 否则还没到期
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert((start + this.excuteTime) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     *比较时间，以最接近执行时间的任务，排在最前面
     */
    @Override
    public int compareTo(Delayed delayed) {
        Worker msg = (Worker) delayed;
        return (int)(((start + this.excuteTime) - System.currentTimeMillis()) -((msg.start + msg.excuteTime) - System.currentTimeMillis())) ;
    }

    public int getType() {
        return this.type;
    }

    public T getBody() {
        return this.body;
    }


    public Worker(int type, T body, long excuteTime) {
        this.type = type;
        this.body = body;
        this.excuteTime = excuteTime;
    }
}