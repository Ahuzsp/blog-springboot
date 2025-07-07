package com.ums.service;

import com.ums.dto.AdminUserRegisterDto;
import com.ums.pojo.AdminUser;
import com.ums.pojo.Menu;

import java.util.List;

public interface AdminService {
    List<Menu> getMenu();

    List<AdminUser> adminUserList();

    AdminUser register(AdminUserRegisterDto adminUserDTO);

    AdminUser findByName(String username);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    void updateLoginTime(Integer id);
}
