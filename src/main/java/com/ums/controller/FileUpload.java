package com.ums.controller;

import cn.hutool.poi.excel.ExcelUtil;
import com.ums.common.CommonResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/file")
public class FileUpload {
    @PostMapping("/upload")
    public CommonResult<List<Map<String, String>>> upload(@RequestBody MultipartFile file) throws Exception {
        boolean isExcel;
        isExcel = file.getOriginalFilename().endsWith("xlsx") || file.getOriginalFilename().endsWith("xls");
        if (!isExcel) {
            return CommonResult.failed("文件格式不对");
        }
        //解析excel里面的链接
        List<Map<String, String>> result =  ExcelUtil.getReader(file.getInputStream()).read().stream().skip(3).filter(Objects::nonNull).map(row -> {
            Map<String, String> map = new HashMap<>();
            map.put("type", row.get(0).toString());
            map.put("name", row.get(1).toString());
            return map;
        }).toList();
        return CommonResult.success(result);
    }
}
