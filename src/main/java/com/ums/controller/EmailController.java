package com.ums.controller;

import com.alibaba.fastjson.JSONObject;
import com.ums.common.CommonResult;
import com.ums.service.EmailService;
import com.ums.utils.RegxPattern;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/sendCode")
    public CommonResult<String> sendCode(@RequestParam("email") String email) {
        try {
            if (!email.contains("@")) {
                return CommonResult.failed("邮箱有误");
            }
            String code = emailService.sendVerificationCode(email);
            log.info("当前验证码为：" + code);
            return CommonResult.success("验证码已发送至您的邮箱，请注意查收");
        } catch (RuntimeException | MessagingException e) {
            return CommonResult.failed("邮件发送失败" + e.getMessage());
        }
    }

    @GetMapping("/testEmail")
    public CommonResult<String> testEmail(@RequestParam("email") String email) {
        boolean isEmail = RegxPattern.isValidEmail(email);
        if (!isEmail) {
            return CommonResult.failed("邮箱格式不正确");
        }
        return CommonResult.success(email);
    }

    @PostMapping("/verifyCode")
    public CommonResult<String> verifyCode(@RequestBody Map<String, String> verify) {
        try {
            String email = verify.get("email");
            if (!email.contains("@")) {
                return CommonResult.failed("邮箱有误");
            }
            if (emailService.verifyCode(verify.get("email"), verify.get("code"))) {
                return CommonResult.success("验证成功");
            } else {
                return CommonResult.failed("验证码错误或已过期");
            }
        } catch (UnsupportedEncodingException e) {
            return CommonResult.failed("操作失败" + e.getMessage());
        }
    }
}
