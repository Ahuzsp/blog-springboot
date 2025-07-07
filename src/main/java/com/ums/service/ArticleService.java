package com.ums.service;

import com.ums.pojo.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getArticleList(Integer category, Integer userId, String keyword);

    List<Article> getArticleListByIds(List<Integer> articleIds);

    Article getArticleDetailById(Integer articleId);

    void addOrUpdateArticle(Article article);

    void deleteArticle(Integer articleId);
}
