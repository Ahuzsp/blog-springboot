package com.ums.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ums.dto.SimpleAdminUserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentBase {
    private Integer id;
    @JsonIgnore
    private Integer userId;
    private Integer articleId;
    private Integer parentId;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Integer status;
    private SimpleAdminUserDto commenterUserInfo;
    private SimpleAdminUserDto repliedToUserInfo;
}

