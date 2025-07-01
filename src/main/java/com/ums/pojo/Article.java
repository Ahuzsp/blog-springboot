package com.ums.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer articleId;
    private Integer userId;
    private String articleTitle;
    private String description;
    private String mdValue;
    private Integer category;
    private Integer commentCount;
    private Integer readCount;
    private Integer likeCount;
    private Integer collectCount;
    private String cover;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
