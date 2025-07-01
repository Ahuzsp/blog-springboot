package com.ums.dto;

import com.ums.dto.page.Pagination;
import lombok.Data;

@Data
public class CommentSearchDto extends Pagination {
    private int articleId;
    private Integer userId;
}
