package com.xiaoxili.userservice.service;

public interface EmailService {
    void sendSimpleMailMessage(String name, String to, String token);
    void sendMimeMailWithAttachments(String name, String to, String token);
}
