package com.ums.mapper;

import com.ums.pojo.Collect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CollectMapper {
    @Select("select * from collect where userId = #{userId}")
    List<Collect> getCollectListByUserId(Integer userId);

    void collectArticle(Collect collect);

    @Delete("delete from collect where collectId = #{collectId}")
    void cancelCollect(Integer collectId);

    @Select("select * from collect where userId = #{userId} and articleId = #{articleId} and createTime = #{createTime}")
    Collect getCollectInsById(Integer userId, Integer articleId, String createTime);
}
