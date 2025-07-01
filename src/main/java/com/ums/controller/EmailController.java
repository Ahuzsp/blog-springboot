package com.ums.controller;

import com.ums.common.CommonResult;
import com.ums.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/sendCode")
    public CommonResult<Boolean> sendCode(@RequestParam("email") String email) {
        try {
            String code = emailService.sendVerificationCode(email);
            log.info("当前验证码为：" + code);
            return CommonResult.success(true);
        } catch (RuntimeException e) {
            return CommonResult.failed("邮件发送失败" + e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
