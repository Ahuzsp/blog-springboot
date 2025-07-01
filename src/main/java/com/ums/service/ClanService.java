package com.ums.service;

import com.ums.pojo.Clan;

import java.util.List;

public interface ClanService {
    void addBatch(List<Clan> clanList);

    List<Clan> getClanList();

    void addOrUpdateClan(Clan clan);
}
