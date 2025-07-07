package com.ums.service.impl;

import com.ums.mapper.ArticleMapper;
import com.ums.mapper.CollectMapper;
import com.ums.pojo.Article;
import com.ums.pojo.Collect;
import com.ums.service.CollectService;
import com.ums.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CollectServiceImp implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Collect> getCollectListByUserId(Integer userId) {
        return collectMapper.getCollectListByUserId(userId);
    }

    @Override
    public void collectArticle(Collect collect) {
        Article article = articleMapper.getArticleDetailById(collect.getArticleId());
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }
        Map<String, Object> loginUser = ThreadLocalUtil.get();
        Integer userId = (Integer) loginUser.get("id");
        // 判断是否已经在收藏表中
        Collect collectIns = getCollectInsById(userId, article.getArticleId(), collect.getCreateTime());
        if (collectIns != null) {
            throw new RuntimeException("该文章已被收藏");
        }
        collect.setUserId(userId);
        collectMapper.collectArticle(collect);
    }

    @Override
    public void cancelCollect(Integer collectId) {
        collectMapper.cancelCollect(collectId);
    }

    @Override
    public Collect getCollectInsById(Integer userId, Integer articleId, String createTime) {
        return collectMapper.getCollectInsById(userId, articleId, createTime);
    }
}
