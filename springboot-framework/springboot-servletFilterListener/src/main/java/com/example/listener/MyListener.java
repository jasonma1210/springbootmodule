package com.example.listener;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionListener;


@Component(value = "myListener")
public class MyListener implements HttpSessionListener {


}
