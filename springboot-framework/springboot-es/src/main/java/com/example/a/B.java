package com.example.a;

import com.example.a.A;
public class B extends A{
    String name = "aaa";
    public B(String name){
        super();
        this.name = name;
        System.out.println("aaaa");
    }

    public static void main(String[] args) {
        A a1 = new B("hp");


    }
}
