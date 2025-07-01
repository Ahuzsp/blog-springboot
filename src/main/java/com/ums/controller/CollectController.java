package com.ums.controller;

import com.ums.common.CommonResult;
import com.ums.pojo.Article;
import com.ums.pojo.Collect;
import com.ums.service.ArticleService;
import com.ums.service.CollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getCollectListByUserId")
    public CommonResult<List<Article>> getCollectListByUserId(Integer userId) {
        try {
            List<Collect> collectList = collectService.getCollectListByUserId(userId);
            List<Integer> ids = collectList.stream()
                    .map(Collect::getArticleId) // 使用方法引用来调用 getArticleId
                    .toList();
            List<Article> articleList = articleService.getArticleListByIds(ids);
            return CommonResult.success(articleList);
        } catch(Exception e) {
            return CommonResult.failed("操作失败" + e.getMessage());
        }
    }

    @PostMapping("/collectArticle")
    public CommonResult<String> collectArticle(@RequestBody Collect collect) {
        try {
            if (collect.getArticleId() == null) {
                return CommonResult.validateFailed("文章id必填");
            }
            collectService.collectArticle(collect);
            return CommonResult.success(null);
        } catch(Exception e) {
            return CommonResult.failed("操作失败" + e.getMessage());
        }
    }
    // 取消收藏
    @GetMapping("/cancelCollect/{collectId}")
    public CommonResult<String> cancelCollect(@PathVariable Integer collectId) {
        try {
            collectService.cancelCollect(collectId);
            return CommonResult.success(null);
        } catch(Exception e)  {
            return CommonResult.failed("操作失败" + e.getMessage());
        }
    }
}
