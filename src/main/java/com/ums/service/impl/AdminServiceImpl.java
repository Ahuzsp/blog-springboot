package com.ums.service.impl;

import com.ums.dto.AdminUserRegisterDto;
import com.ums.mapper.AdminMapper;
import com.ums.pojo.AdminUser;
import com.ums.pojo.Menu;
import com.ums.service.AdminService;
import com.ums.utils.CryptoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Menu> getMenu() {
        return adminMapper.getMenu();
    }

    @Override
    public List<AdminUser> adminUserList() {
        return adminMapper.adminUserList();
    }

    @Override
    public AdminUser register(AdminUserRegisterDto adminUserDTO) {
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(adminUserDTO, adminUser);
        adminUser.setStatus(1);
        adminUser.setCreateTime(LocalDateTime.now());
        // 查询是否有同名用户
        AdminUser user = adminMapper.findByName(adminUser.getUsername());
        if (user != null) {
            return null;
        }
        // 密码加密
        String hashPassword = CryptoUtil.hashPassword(adminUser.getPassword());
        adminUser.setPassword(hashPassword);
        adminMapper.register(adminUser);
        return adminUser;
    }

    @Override
    public AdminUser findByName(String username) {
        return adminMapper.findByName(username);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        adminMapper.deleteBatch(ids);
    }

    @Override
    public void updateLoginTime(Integer id) {
        adminMapper.updateLoginTime(id);
    }
}
