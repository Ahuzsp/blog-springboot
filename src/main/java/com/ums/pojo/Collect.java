package com.ums.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Collect {
    private Integer collectId;
    private Integer articleId;
    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime collectTime;
    // 文章发布时间
    private String createTime;
}
