package com.ums.dto;

import lombok.Data;

@Data
public class AdminUserRegisterDto {
    private String username;
    private String password;
    private String icon;
    private String email;
    private String nickName;
    private String note;
    private Integer status;
}
