package com.ums.service.impl;

import com.ums.mapper.ArticleMapper;
import com.ums.pojo.Article;
import com.ums.service.ArticleService;
import com.ums.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ArticleServiceImp implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> getArticleList(Integer category, Integer userId, String keyword) {
        return articleMapper.getArticleList(category, userId, keyword);
    }

    @Override
    public List<Article> getArticleListByIds(List<Integer> articleIds) {
        return articleMapper.getArticleListByIds(articleIds);
    }

    @Override
    public Article getArticleDetailById(Integer articleId) {
        return articleMapper.getArticleDetailById(articleId);
    }

    @Override
    public void addOrUpdateArticle(Article article) {
        Map <String, Object> loginUser = ThreadLocalUtil.get();
        if (article.getArticleId() == null) {
            article.setUserId((Integer) loginUser.get("id"));
            articleMapper.addArticle(article);
        } else {
            Integer userId = article.getUserId();
            Integer loginUserId = (Integer) loginUser.get("id");
            if (!Objects.equals(userId, loginUserId)) {
                throw new RuntimeException("该文章不是您所创建，您暂无权限修改");
            }
            articleMapper.updateArticle(article);
        }
    }

    @Override
    public void deleteArticle(Integer articleId) {
        Map <String, Object> loginUser = ThreadLocalUtil.get();
        Integer loginUserId = (Integer) loginUser.get("id");
        Article article = articleMapper.getArticleDetailById(articleId);
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }
        if (article.getUserId() != null && !Objects.equals(loginUserId, article.getUserId())) {
            throw new RuntimeException("该文章不是您所创建，您暂无权限删除");
        }
        articleMapper.deleteArticle(articleId);
    }
}
