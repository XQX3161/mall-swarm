package com.macro.common.service.impl;

import com.macro.common.entity.User;
import com.macro.common.mapper.UserMapper;
import com.macro.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * UserService 实现类：完成基本的 CRUD 逻辑，并做简单参数校验
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user 不能为空");
        }
        if (!StringUtils.hasText(user.getUsername())) {
            throw new IllegalArgumentException("username 不能为空");
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new IllegalArgumentException("password 不能为空");
        }
        // 设置默认值
        user.setStatus(user.getStatus() == null ? 1 : user.getStatus());
        Date now = new Date();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        userMapper.insert(user);
        return user;
    }

    @Override
    public User update(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("id 不能为空");
        }
        user.setUpdatedAt(new Date());
        int rows = userMapper.updateById(user);
        if (rows <= 0) return null;
        return userMapper.selectById(user.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) return false;
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public User getById(Long id) {
        if (id == null) return null;
        return userMapper.selectById(id);
    }

    @Override
    public User getByUsername(String username) {
        if (!StringUtils.hasText(username)) return null;
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<User> listAll() {
        return userMapper.selectAll();
    }
}
