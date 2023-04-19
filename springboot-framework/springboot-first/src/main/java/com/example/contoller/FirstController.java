package com.example.contoller;

import com.example.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
public class FirstController {

    @GetMapping("/first")
    public ResultVO first(){

        return ResultVO.success("200","成功","first success");
    }

//
//    public String aa() throws FileNotFoundException {
//        InputStream is = new FileInputStream("aaaaa");    //显式定义的异常 CheckedException
//        try {
//            int a = 3 / 0;   //runtimeException
//        }catch (ArithmeticException e){
//            e.printStackTrace();
//        }
//        return "";
//    }
}
