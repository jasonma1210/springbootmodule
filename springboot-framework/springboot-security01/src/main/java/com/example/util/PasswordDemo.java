package com.example.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordDemo {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
        System.out.println("--------------------------------------------");
        System.out.println(passwordEncoder.encode("123456"));
        System.out.println("--------------------------------------------");
        System.out.println(passwordEncoder.matches("123456",passwordEncoder.encode("123456")));
        //md5(md5("123")+salt)

    }
}
