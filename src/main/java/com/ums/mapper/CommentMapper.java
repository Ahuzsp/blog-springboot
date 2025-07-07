package com.ums.mapper;

import com.ums.dto.CommentSearchDto;
import com.ums.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> getComments(CommentSearchDto searchQuery);

    void addComment(Comment comment);

    void deleteComment(Integer commentId);
}
