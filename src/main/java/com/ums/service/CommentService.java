package com.ums.service;

import com.ums.dto.CommentSearchDto;
import com.ums.pojo.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getComments(CommentSearchDto searchQuery);

    void addComment(Comment comment);

    void deleteComment(Integer commentId);
}
