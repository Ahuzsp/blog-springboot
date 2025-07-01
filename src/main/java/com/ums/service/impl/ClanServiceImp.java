package com.ums.service.impl;

import com.ums.mapper.ClanMapper;
import com.ums.pojo.Clan;
import com.ums.service.ClanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClanServiceImp implements ClanService {

    @Autowired
    private ClanMapper clanMapper;

    @Override
    public void addBatch(List<Clan> clanList) {
        clanMapper.addBatch(clanList);
    }

    @Override
    public List<Clan> getClanList() {
        return clanMapper.getCLanList();
    }

    @Override
    public void addOrUpdateClan(Clan clan) {
        Integer id = clan.getId();
        if (id == null) {
            clanMapper.add(clan);
        } else {
            clanMapper.update(clan);
        }
    }
}
