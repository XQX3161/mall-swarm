package com.macro.common.service;

import com.macro.common.model.User;
import java.util.List;

/**
 * 用户业务服务接口
 * 定义用户相关的业务操作规范
 *
 * @author macro
 */
public interface UserService {

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 是否添加成功
     */
    boolean addUser(User user);

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID
     * @return 是否删除成功
     */
    boolean deleteUserById(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 用户ID列表
     * @return 删除的用户数量
     */
    int deleteUsersByIds(List<Long> ids);

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     * @return 是否更新成功
     */
    boolean updateUser(User user);

    /**
     * 根据用户ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户对象
     */
    User getUserById(Long id);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);

    /**
     * 根据邮箱查询用户信息
     *
     * @param email 邮箱
     * @return 用户对象
     */
    User getUserByEmail(String email);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> getAllUsers();

    /**
     * 分页查询用户列表
     *
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页数量
     * @return 用户列表
     */
    List<User> getUsersByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据状态查询用户列表
     *
     * @param status 用户状态（0-禁用，1-启用）
     * @return 用户列表
     */
    List<User> getUsersByStatus(Integer status);

    /**
     * 获取用户总数
     *
     * @return 用户总数
     */
    Long getUserCount();

    /**
     * 获取指定状态的用户数量
     *
     * @param status 用户状态
     * @return 用户数量
     */
    Long getUserCountByStatus(Integer status);

    /**
     * 用户登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户对象，失败返回null
     */
    User login(String username, String password);

    /**
     * 检查用户名是否已存在
     *
     * @param username 用户名
     * @return true表示已存在，false表示不存在
     */
    boolean isUsernameExists(String username);

    /**
     * 检查邮箱是否已存在
     *
     * @param email 邮箱
     * @return true表示已存在，false表示不存在
     */
    boolean isEmailExists(String email);

    /**
     * 启用用户
     *
     * @param id 用户ID
     * @return 是否启用成功
     */
    boolean enableUser(Long id);

    /**
     * 禁用用户
     *
     * @param id 用户ID
     * @return 是否禁用成功
     */
    boolean disableUser(Long id);

    /**
     * 重置用户密码
     *
     * @param id 用户ID
     * @param newPassword 新密码
     * @return 是否重置成功
     */
    boolean resetPassword(Long id, String newPassword);
}
