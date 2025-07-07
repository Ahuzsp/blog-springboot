package com.ums.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    String sendVerificationCode(String email) throws MessagingException;

    boolean verifyCode(String email, String code) throws UnsupportedEncodingException;
}
