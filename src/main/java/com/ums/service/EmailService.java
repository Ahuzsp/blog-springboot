package com.ums.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    String sendVerificationCode(String email) throws MessagingException;
}
