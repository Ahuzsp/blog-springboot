package com.ums.controller;

import com.alibaba.fastjson.JSON;
import com.ums.common.CommonResult;
import com.ums.dto.CommentDto;
import com.ums.dto.CommentSearchDto;
import com.ums.pojo.Comment;
import com.ums.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/getComments")
    public CommonResult<Object> getComments(@RequestBody CommentSearchDto searchQuery) {
        try {
            List<Comment> commentList = commentService.getComments(searchQuery);
            List<CommentDto> commentDtoList = new ArrayList<>();
            commentList.forEach(comment -> {
                CommentDto dto = new CommentDto();
                // 复制所有同名属性（忽略imgListJson，因为它需要特殊处理）
                BeanUtils.copyProperties(comment, dto, "imgList");
                // 单独处理 imgList
                if (comment.getImgList() != null) {
                    dto.setImgList(JSON.parseArray(comment.getImgList(), String.class));
                }
                commentDtoList.add(dto);
            });
            return CommonResult.success(commentDtoList);
        } catch (Exception e) {
            return CommonResult.failed("操作失败" + e.getMessage());
        }
    }

    @PostMapping("/addComment")
    public CommonResult<Object> addComment(@RequestBody CommentDto comment) {
        try {
            Comment commentParams = new Comment();
            BeanUtils.copyProperties(comment, commentParams, "imgList");
            if (comment.getImgList() != null) {
                commentParams.setImgList(JSON.toJSONString(comment.getImgList()));
            }
            commentService.addComment(commentParams);
            return CommonResult.success(null);
        } catch (RuntimeException e) {
            return CommonResult.failed("操作失败" + e.getMessage());
        }
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public CommonResult<String> deleteComment(@PathVariable Integer commentId) {
        try {
            commentService.deleteComment(commentId);
            return CommonResult.success(null);
        } catch (RuntimeException e) {
            return CommonResult.failed("操作失败" + e.getMessage());
        }
    }
}
