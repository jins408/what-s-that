package com.web.dictionary.service;


import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl {


    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "koreanculturedictionary@gmail.com";

    private Integer code() throws Exception {
        Random rand = new Random();
        int code = rand.nextInt(90000) + 10000;
        return code;
    }

    private String makePassword() throws Exception {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 10; i++) {
            if (random.nextBoolean()) {
                sb.append((char) ((int) (random.nextInt(26)) + 97));
            } else {
                sb.append((random.nextInt(10)));
            }
        }
        return sb.toString();

    }

    public String mailSend(String email) throws Exception {
        String authcode = code().toString();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(EmailServiceImpl.FROM_ADDRESS);
        message.setSubject("회원가입 인증코드 입니다.");
        message.setText("인증번호 = " + authcode);

        mailSender.send(message);
        return authcode;
    }

    public String passwordSend(String email) throws Exception {
        String password = makePassword();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(EmailServiceImpl.FROM_ADDRESS);
        message.setSubject("새로운 비밀번호는 입니다.");
        message.setText("새로운 비밀번호 = " + password);

        mailSender.send(message);
        return password;
    }


}
