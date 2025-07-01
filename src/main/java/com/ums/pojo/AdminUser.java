package com.ums.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL) // 不返回null的字段
public class AdminUser {
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String icon;
    private String email;
    private String nickName;
    private String note;
    private LocalDateTime createTime;
    private LocalDateTime loginTime;
    private Integer status;
}
