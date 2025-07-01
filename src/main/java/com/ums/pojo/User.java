package com.ums.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String description;
    private String authorAvatar;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
