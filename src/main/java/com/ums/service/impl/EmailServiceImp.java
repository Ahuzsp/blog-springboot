package com.ums.service.impl;

import com.ums.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmailServiceImp implements EmailService {
    private static final int EXPIRE_TIME = 5; // 5分钟
    private static final String SUBJECT = "您的验证码";
    private static final String CODE_PREFIX = "verification_code:";
    private static final String VERIFICATION_CODE_LAST_SEND_TIME = "verification_code_last_sent_time:";

    @Autowired
    private StringRedisTemplate StringRedisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String sendVerificationCode(String toEmail) throws MessagingException {

        // 检查是否已发送过验证码且未超过设定的时间间隔
        if (!canSendCode(toEmail)) {
            throw new MessagingException("验证码发送过于频繁，请稍后再试。");
        }

        // 生成6位随机验证码
        String code = String.format("%06d", new Random().nextInt(999999));

        // 创建带样式的HTML邮件内容
        String content = buildEmailContent(code);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(SUBJECT);
        helper.setText(content, true); // true表示这是HTML内容

        mailSender.send(message);
        saveCode(toEmail, code);
        return code;
    }

    private boolean canSendCode(String toEmail) {
        // 检查toEmail对应的上一次发送时间是否存在且未超过一分钟
        String lastSentTimeKey = VERIFICATION_CODE_LAST_SEND_TIME + toEmail;
        String lastSentTime = stringRedisTemplate.opsForValue().get(lastSentTimeKey);
        if (lastSentTime != null && !lastSentTime.isEmpty()) {
            long lastTime = Long.parseLong(lastSentTime);
            // 检查时间差是否小于1分钟
            return System.currentTimeMillis() - lastTime >= 60_000;
        }
        return true;
    }

    @Override
    public boolean verifyCode(String email, String code) {
        String storedCode = StringRedisTemplate.opsForValue().get(CODE_PREFIX + URLDecoder.decode(email, StandardCharsets.UTF_8));
        return code != null && code.equals(storedCode);
    }

    private void saveCode(String email, String code) {
        StringRedisTemplate.opsForValue().set(CODE_PREFIX + email, code, EXPIRE_TIME, TimeUnit.MINUTES);

        // 同时保存当前发送邮件时间
        String lastSentTimeKey = VERIFICATION_CODE_LAST_SEND_TIME + email;
        stringRedisTemplate.opsForValue().set(lastSentTimeKey, String.valueOf(System.currentTimeMillis()));
        // 设置键的过期时间为1分钟
        stringRedisTemplate.expire(lastSentTimeKey, 1, TimeUnit.MINUTES);
    }

    private String buildEmailContent(String code) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f5f5f5; margin: 0; padding: 20px; }" +
                "        .container { max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 30px; border-radius: 8px; }" +
                "        .header { color: #333333; text-align: center; }" +
                "        .code { font-size: 26px; font-weight: bold; color: #1a73e8; text-align: center; margin: 20px 0; padding: 10px; background-color: #f0f7ff; border-radius: 4px; letter-spacing: 3px; }" +
                "        .footer { margin-top: 30px; text-align: center; color: #666666; font-size: 12px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <h1 class='header'>验证码</h1>" +
                "        <p>您好，您正在尝试进行验证操作，您的验证码是：</p>" +
                "        <div class='code'>" + code + "</div>" +
                "        <p>请在5分钟内使用此验证码完成验证。</p>" +
                "        <p>如果您没有请求此验证码，请忽略此邮件。</p>" +
                "        <div class='footer'>" +
                "            <p>© 2023-2025 ACME. 保留所有权利。</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}
