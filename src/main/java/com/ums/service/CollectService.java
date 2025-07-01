package com.ums.service;

import com.ums.pojo.Collect;

import java.util.List;

public interface CollectService {
    List<Collect> getCollectListByUserId(Integer userId);

    void collectArticle(Collect collect);

    void cancelCollect(Integer collectId);

    Collect getCollectInsById(Integer userId, Integer articleId, String createTime);
}
