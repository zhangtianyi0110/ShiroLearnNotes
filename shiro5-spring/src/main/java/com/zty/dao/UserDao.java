package com.zty.dao;

import com.zty.pojo.User;

import java.util.List;

public interface UserDao {
    User getUserByUserName(String userName);
    List<String>queryRolesByUserName(String userName);
    List<String> queryPermsByRoleName(String roleName);
}
