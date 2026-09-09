package com.macro.common.service.impl;

import com.macro.common.mapper.UserMapper;
import com.macro.common.model.User;
import com.macro.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * 用户业务服务实现类
 * 实现UserService接口，封装用户相关的业务逻辑
 *
 * @author macro
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 注入UserMapper数据访问对象
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 新增用户
     * 将用户对象保存到数据库
     *
     * @param user 用户对象
     * @return 是否添加成功
     */
    @Override
    public boolean addUser(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        // 对密码进行MD5加密
        user.setPassword(encryptPassword(user.getPassword()));
        return userMapper.insert(user) > 0;
    }

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteUserById(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return userMapper.deleteById(id) > 0;
    }

    /**
     * 批量删除用户
     *
     * @param ids 用户ID列表
     * @return 删除的用户数量
     */
    @Override
    public int deleteUsersByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        return userMapper.deleteByIds(ids);
    }

    /**
     * 更新用户信息
     * 更新用户的基本信息到数据库
     *
     * @param user 用户对象
     * @return 是否更新成功
     */
    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() == null || user.getId() <= 0) {
            return false;
        }
        // 如果修改了密码，进行MD5加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(encryptPassword(user.getPassword()));
        }
        return userMapper.update(user) > 0;
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户对象，不存在则返回null
     */
    @Override
    public User getUserById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return userMapper.selectById(id);
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户对象，不存在则返回null
     */
    @Override
    public User getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        return userMapper.selectByUsername(username);
    }

    /**
     * 根据邮箱查询用户信息
     *
     * @param email 邮箱
     * @return 用户对象，不存在则返回null
     */
    @Override
    public User getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return userMapper.selectByEmail(email);
    }

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    /**
     * 分页查询用户列表
     * 根据页码和每页数量进行分页查询
     *
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页数量
     * @return 用户列表
     */
    @Override
    public List<User> getUsersByPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum <= 0 || pageSize == null || pageSize <= 0) {
            return null;
        }
        // 计算偏移量：offset = (pageNum - 1) * pageSize
        Integer offset = (pageNum - 1) * pageSize;
        return userMapper.selectByPage(offset, pageSize);
    }

    /**
     * 根据状态查询用户列表
     *
     * @param status 用户状态（0-禁用，1-启用）
     * @return 用户列表
     */
    @Override
    public List<User> getUsersByStatus(Integer status) {
        if (status == null) {
            return null;
        }
        return userMapper.selectByStatus(status);
    }

    /**
     * 获取用户总数
     *
     * @return 用户总数
     */
    @Override
    public Long getUserCount() {
        return userMapper.count();
    }

    /**
     * 获取指定状态的用户数量
     *
     * @param status 用户状态
     * @return 用户数量
     */
    @Override
    public Long getUserCountByStatus(Integer status) {
        if (status == null) {
            return 0L;
        }
        return userMapper.countByStatus(status);
    }

    /**
     * 用户登录验证
     * 验证用户名和密码是否正确
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户对象，失败返回null
     */
    @Override
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return null;
        }

        // 根据用户名查询用户
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }

        // 验证密码
        String encryptedPassword = encryptPassword(password);
        if (encryptedPassword.equals(user.getPassword())) {
            return user;
        }

        return null;
    }

    /**
     * 检查用户名是否已存在
     *
     * @param username 用户名
     * @return true表示已存在，false表示不存在
     */
    @Override
    public boolean isUsernameExists(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        User user = userMapper.selectByUsername(username);
        return user != null;
    }

    /**
     * 检查邮箱是否已存在
     *
     * @param email 邮箱
     * @return true表示已存在，false表示不存在
     */
    @Override
    public boolean isEmailExists(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        User user = userMapper.selectByEmail(email);
        return user != null;
    }

    /**
     * 启用用户
     * 将用户状态设置为启用（1）
     *
     * @param id 用户ID
     * @return 是否启用成功
     */
    @Override
    public boolean enableUser(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            return false;
        }
        user.setStatus(1);
        return userMapper.update(user) > 0;
    }

    /**
     * 禁用用户
     * 将用户状态设置为禁用（0）
     *
     * @param id 用户ID
     * @return 是否禁用成功
     */
    @Override
    public boolean disableUser(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            return false;
        }
        user.setStatus(0);
        return userMapper.update(user) > 0;
    }

    /**
     * 重置用户密码
     * 为指定用户重新设置密码
     *
     * @param id 用户ID
     * @param newPassword 新密码
     * @return 是否重置成功
     */
    @Override
    public boolean resetPassword(Long id, String newPassword) {
        if (id == null || id <= 0 || newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            return false;
        }
        // 对新密码进行MD5加密
        user.setPassword(encryptPassword(newPassword));
        return userMapper.update(user) > 0;
    }

    /**
     * 密码加密方法
     * 使用MD5算法对密码进行加密
     *
     * @param password 原始密码
     * @return 加密后的密码
     */
    private String encryptPassword(String password) {
        if (password == null) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
