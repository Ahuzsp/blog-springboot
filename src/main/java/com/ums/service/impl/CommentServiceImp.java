package com.ums.service.impl;

import com.ums.dto.CommentSearchDto;
import com.ums.mapper.CommentMapper;
import com.ums.pojo.Comment;
import com.ums.service.CommentService;
import com.ums.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getComments(CommentSearchDto searchQuery) {
        return commentMapper.getComments(searchQuery);
    }

    @Override
    public void addComment(Comment comment) {
        Map <String, Object> loginUser = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) loginUser.get("id");
        comment.setUserId(loginUserId);
        commentMapper.addComment(comment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentMapper.deleteComment(commentId);
    }
}
