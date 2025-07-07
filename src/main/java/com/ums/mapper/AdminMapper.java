package com.ums.mapper;

import com.ums.pojo.AdminUser;
import com.ums.pojo.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from ums_menu")
    List<Menu> getMenu();

    List<AdminUser> adminUserList();

    void register(AdminUser adminUser);

    @Select("select * from ums_admin where username=#{username}")
    AdminUser findByName(String username);

    @Delete("delete from ums_admin where id=#{id}")
    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    @Update("update ums_admin set login_time=now() where id=#{id}")
    void updateLoginTime(Integer id);
}
