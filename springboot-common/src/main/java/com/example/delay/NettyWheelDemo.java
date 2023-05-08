package com.example.delay;

import io.netty.util.HashedWheelTimer;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NettyWheelDemo {

    public static void main(String[] args) {
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(Executors.defaultThreadFactory(),1,TimeUnit.SECONDS,12);
        System.out.println("开始执行时间:"+ LocalDateTime.now());
        hashedWheelTimer.newTimeout((timeout)->{
            System.out.println("5S执行时间后是"+LocalDateTime.now());
        },5, TimeUnit.SECONDS);
        hashedWheelTimer.newTimeout((timeout)->{
            System.out.println("10S执行时间后是"+LocalDateTime.now());
        },10, TimeUnit.SECONDS);
        hashedWheelTimer.newTimeout((timeout)->{
            System.out.println("8S执行时间后是"+LocalDateTime.now());
        },8, TimeUnit.SECONDS);
        hashedWheelTimer.newTimeout((timeout)->{
            System.out.println("16S执行时间后是"+LocalDateTime.now());
        },16, TimeUnit.SECONDS);
    }
}
