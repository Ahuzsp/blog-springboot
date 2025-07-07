package com.ums.controller;

import com.ums.common.CommonResult;
import com.ums.pojo.Article;
import com.ums.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    // 获取文章列表（参数可选：分类，用户id以及关键词）
    @GetMapping("/getArticleList")
    public CommonResult<List<Article>> getArticleList(@RequestParam(required = false) Integer category, @RequestParam(required = false) Integer userId, @RequestParam(required = false) String keyword) {
        try {
            List<Article> articleList = articleService.getArticleList(category, userId, keyword);
            return CommonResult.success(articleList);
        } catch (Exception e) {
            return CommonResult.failed("获取文章失败: " + e.getMessage());
        }
    }
    // 通过文章id获取详情
    @GetMapping("/getArticleDetailById")
    public CommonResult<Article> getArticleDetailById(@RequestParam Integer articleId) {
        if (articleId == null) {
            return CommonResult.failed("参数错误，缺少文章id");
        }
        Article articleDetail = articleService.getArticleDetailById(articleId);
        return CommonResult.success(articleDetail);
    }

    // 新增或修改文章信息
    @PostMapping("/addOrUpdateArticle")
    public CommonResult<String> addOrUpdateArticle(@RequestBody Article article) {
        try {
            articleService.addOrUpdateArticle(article);
            return CommonResult.success(null);
        } catch (Exception e) {
            return CommonResult.failed("操作失败" + (e.getMessage() != null ? ("，" + e.getMessage()) : ""));
        }
    }

    // 删除文章
    @DeleteMapping("/deleteArticle/{articleId}")
    public CommonResult<String> deleteArticle(@PathVariable Integer articleId) {
        try {
            articleService.deleteArticle(articleId);
            return CommonResult.success(null);
        } catch (Exception e) {
            return CommonResult.failed("操作失败" + (e.getMessage() != null ? ("，" + e.getMessage()) : ""));
        }
    }
}
