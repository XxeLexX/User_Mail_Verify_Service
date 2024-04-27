package com.xiaoxili.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.xiaoxili.userservice.service.EmailService;
import com.xiaoxili.userservice.utils.EmailUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    @Value("${spring.mail.verifyhost}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private final JavaMailSender emailSender;

    // send a simple text mail
    @Override
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage simpleMessage = new SimpleMailMessage();
            simpleMessage.setSubject("Demo Test for Verify Account");
            simpleMessage.setFrom(fromEmail);
            simpleMessage.setTo(to);
            simpleMessage.setText(EmailUtils.getEmailMessage(name, host, token));
            emailSender.send(simpleMessage);
        } catch (Exception e) {
            throw new RuntimeException("Damn, somthing wrong with -> " + e.getMessage());
        }
    }

    @Override
    public void sendMimeMailWithAttachments(String name, String to, String token) {
        // TODO Auto-generated method stub
    }
    
}
