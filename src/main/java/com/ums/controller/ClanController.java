package com.ums.controller;

import com.ums.common.CommonResult;
import com.ums.pojo.Clan;
import com.ums.service.ClanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clan")
public class ClanController {
    @Autowired
    private ClanService clanService;

    @PostMapping("/addBatch")
    public CommonResult<Object> addBatch(@RequestBody List<Clan> clanList) {
        if (clanList == null || clanList.isEmpty()) {
            return CommonResult.failed("列表为空");
        }
        try {
            clanService.addBatch(clanList);
            return CommonResult.success(null);
        } catch (Exception e) {
            return CommonResult.failed("添加失败: " + e.getMessage());
        }
    }

    @GetMapping("/clanList")
    public CommonResult<List<Clan>> clanList() {
        try {
            List<Clan> clanList = clanService.getClanList();
            return CommonResult.success(clanList);
        } catch (Exception e) {
            return CommonResult.failed("获取成员列表失败" + e.getMessage());
        }
    }

    @PostMapping("/addOrUpdateClan")
    public CommonResult<String> addOrUpdateClan(@RequestBody Clan clan) {
        try {
            clanService.addOrUpdateClan(clan);
            return CommonResult.success("操作成功");
        } catch (Exception e) {
            return CommonResult.failed("操作失败: " + e.getMessage());
        }
    }

}
