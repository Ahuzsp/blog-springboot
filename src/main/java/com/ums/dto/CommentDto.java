package com.ums.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ums.base.CommentBase;
import lombok.Data;

import java.util.List;

@Data
public class CommentDto extends CommentBase {
    private List<String> imgList;
    private Integer repliedUserId;
}
