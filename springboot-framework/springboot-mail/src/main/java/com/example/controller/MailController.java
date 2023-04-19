package com.example.controller;



import com.example.vo.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

@RestController
public class MailController {
    @Resource
    private JavaMailSender javaMailSender;

    @Value("${my.mail}")
    private String myMail;



    @GetMapping("/mailto")
    public ResultVO mailTo(String subject, String context, String sendto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        //设置邮件内容
        message.setText(String.valueOf(new Random().nextInt(999999)));
        //设置邮件发送给谁，可以多个，这里就发给你的QQ邮箱
        message.setTo(sendto);
        //邮件发送者，这里要与配置文件中的保持一致
        message.setFrom(myMail);
        //OK，万事俱备只欠发送
        javaMailSender.send(message);
        return ResultVO.success("200","succss");
    }

}
