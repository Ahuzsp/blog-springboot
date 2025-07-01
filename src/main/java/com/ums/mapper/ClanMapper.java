package com.ums.mapper;

import com.ums.pojo.Clan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClanMapper {
    void addBatch(List<Clan> clanList);

    @Select("select * from clan")
    List<Clan> getCLanList();

    void add(Clan clan);

    void update(Clan clan);
}
