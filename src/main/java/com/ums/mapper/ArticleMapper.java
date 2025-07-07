package com.ums.mapper;

import com.ums.pojo.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Select("select * from article where articleId=#{articleId}")
    Article getArticleDetailById(Integer articleId);

    List<Article> getArticleList(Integer category, Integer userId, String keyword);

    List<Article> getArticleListByIds(List<Integer> articleIds);

    void addArticle(Article article);

    void updateArticle(Article article);

    @Delete("delete from article where articleId = #{articleId}")
    void deleteArticle(Integer articleId);
}
