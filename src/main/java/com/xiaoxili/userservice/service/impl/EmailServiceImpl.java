package com.xiaoxili.userservice.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.xiaoxili.userservice.service.EmailService;
import com.xiaoxili.userservice.utils.EmailUtils;

import jakarta.mail.internet.MimeMessage;
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
            simpleMessage.setSubject("(Test) Please Verify Account");
            simpleMessage.setFrom(fromEmail);
            simpleMessage.setTo(to);
            simpleMessage.setText(EmailUtils.getEmailMessage(name, host, token));
            emailSender.send(simpleMessage);
        } catch (Exception e) {
            throw new RuntimeException("Damn, somthing wrong with -> " + e.getMessage());
        }
    }

    @Override
    public void sendMimeMailWithAttachments(String name, String to, String pathToAttachment) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("(Test) Account verified");
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText("Good Job!\n" + "Welcome Onboard!\n");

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            mimeMessageHelper.addAttachment(file.getFilename(), file);

            emailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Something wrong while sending attachmens: " + e.getMessage());
        }
    }
    
}
