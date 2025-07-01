package com.ums.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ums.base.CommentBase;
import lombok.Data;

@Data
public class Comment extends CommentBase {
    private String imgList;
    @JsonIgnore
    private Integer repliedUserId;
}
