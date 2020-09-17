package com.web.dictionary.service;


import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl{

	
	private JavaMailSender mailSender;
	private static final String FROM_ADDRESS = "koreanculturedictionary@gmail.com";
	
	private Integer code() {
		Random rand = new Random();
		int code = rand.nextInt(90000) + 10000;
		return code;
	}
	
    public String mailSend(String email) {
    	String authcode = code().toString();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(EmailServiceImpl.FROM_ADDRESS);
        message.setSubject("회원가입 인증코드 입니다.");
        message.setText("인증번호 = "+ authcode ) ;

        mailSender.send(message);
		return authcode;
    }

	
}
