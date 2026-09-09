package com.macro.common.service;

import com.macro.common.entity.User;

import java.util.List;

/**
 * UserService 接口，定义用户相关业务操作
 */
public interface UserService {

    User create(User user);

    User update(User user);

    boolean deleteById(Long id);

    User getById(Long id);

    User getByUsername(String username);

    List<User> listAll();
}
