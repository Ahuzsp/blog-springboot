package com.ums.controller;

import com.ums.common.CommonResult;
import com.ums.dto.AdminUserRegisterDto;
import com.ums.pojo.AdminUser;
import com.ums.pojo.Menu;
import com.ums.service.AdminService;
import com.ums.utils.CryptoUtil;
import com.ums.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/menu")
    public List<Menu> adminMenu() {
        return adminService.getMenu();
    }

    @GetMapping("/user")
    public CommonResult<AdminUser> findByName(@RequestParam String username) {
        AdminUser user = adminService.findByName(username);
        if (user == null) {
            return CommonResult.failed("用户不存在");
        }
        return CommonResult.success(user);
    }

    @GetMapping("/userList")
    public List<AdminUser> adminUserList() {
        return adminService.adminUserList();
    }

    @DeleteMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable Integer id) {
        try {
            adminService.deleteById(id);
            return CommonResult.success(null);
        } catch (Exception e) {
            return CommonResult.failed("删除失败: " + e.getMessage());
        }
    }

    // 批量删除
    @PostMapping("/deleteBatch")
    public CommonResult<Object> deleteBatch(@RequestBody Map<String, List<Integer>> idsmap) {
        try {
            adminService.deleteBatch(idsmap.get("ids"));
            return CommonResult.success(null);
        } catch (Exception e) {
            return CommonResult.failed("删除失败: " + e.getMessage());
        }
    }


    // 注册用户
    @PostMapping("/register")
    public CommonResult<AdminUser> register(@RequestBody AdminUserRegisterDto adminUserDTO) {
        AdminUser user = adminService.register(adminUserDTO);
        if (user == null) {
            return CommonResult.failed("注册失败");
        }
        return CommonResult.success(user);

    }

    // 登录
    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody Map<String, String> loginMap) {
        String username = loginMap.get("username");
        String password = loginMap.get("password");
        AdminUser adminUser = adminService.findByName(username);
        if (adminUser == null) {
            return CommonResult.failed("用户不存在");
        }
        String hashPassword = adminUser.getPassword();
        if (!CryptoUtil.verifyPassword(password, hashPassword)) {
            return CommonResult.failed("密码错误");
        }
        // 调用JwtUtil生成token
        String token = JwtUtil.generateToken(Map.of("username", username, "id", adminUser.getId()));
        // 清除之前的redisToken
        stringRedisTemplate.opsForValue().getOperations().delete("loginToken");
        // 更新最后登录时间
        adminService.updateLoginTime(adminUser.getId());
        // 将token存到redis
        stringRedisTemplate.opsForValue().set("loginToken", token, 1, TimeUnit.DAYS);
        return CommonResult.success(token);
    }
}
