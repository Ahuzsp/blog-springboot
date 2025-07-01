package com.ums.service.impl;

import com.ums.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImp implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public String sendVerificationCode(String toEmail) throws MessagingException {

        // 生成6位随机验证码
        String code = String.format("%06d", new Random().nextInt(999999));

        // 创建带样式的HTML邮件内容
        String subject = "您的验证码";
        String content = buildEmailContent(code);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true); // true表示这是HTML内容

        mailSender.send(message);

        return code;
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
