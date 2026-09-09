package com.macro.common.controller;

import com.macro.common.api.CommonResult;
import com.macro.common.model.User;
import com.macro.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 * 处理用户相关的HTTP请求，提供RESTful API接口
 *
 * @author macro
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 注入用户业务服务
     */
    @Autowired
    private UserService userService;

    /**
     * 新增用户
     * POST请求：/user/add
     *
     * @param user 用户对象
     * @return 返回操作结果，成功返回CommonResult.success()
     */
    @PostMapping("/add")
    public CommonResult addUser(@RequestBody User user) {
        // 参数校验
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return CommonResult.failed("用户名和密码不能为空");
        }

        // 检查用户名是否已存在
        if (userService.isUsernameExists(user.getUsername())) {
            return CommonResult.failed("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (user.getEmail() != null && userService.isEmailExists(user.getEmail())) {
            return CommonResult.failed("邮箱已存在");
        }

        // 默认设置用户状态为启用（1）
        if (user.getStatus() == null) {
            user.setStatus(1);
        }

        boolean result = userService.addUser(user);
        if (result) {
            return CommonResult.success(user, "用户添加成功");
        }
        return CommonResult.failed("用户添加失败");
    }

    /**
     * 根据用户ID查询用户信息
     * GET请求：/user/{id}
     *
     * @param id 用户ID
     * @return 返回用户对象，不存在返回失败信息
     */
    @GetMapping("/{id}")
    public CommonResult getUserById(@PathVariable Long id) {
        // 参数校验
        if (id == null || id <= 0) {
            return CommonResult.failed("用户ID不能为空或无效");
        }

        User user = userService.getUserById(id);
        if (user != null) {
            return CommonResult.success(user);
        }
        return CommonResult.failed("用户不存在");
    }

    /**
     * 根据用户名查询用户信息
     * GET请求：/user/username/{username}
     *
     * @param username 用户名
     * @return 返回用户对象，不存在返回失败信息
     */
    @GetMapping("/username/{username}")
    public CommonResult getUserByUsername(@PathVariable String username) {
        // 参数校验
        if (username == null || username.trim().isEmpty()) {
            return CommonResult.failed("用户名不能为空");
        }

        User user = userService.getUserByUsername(username);
        if (user != null) {
            return CommonResult.success(user);
        }
        return CommonResult.failed("用户不存在");
    }

    /**
     * 根据邮箱查询用户信息
     * GET请求：/user/email/{email}
     *
     * @param email 邮箱
     * @return 返回用户对象，不存在返回失败信息
     */
    @GetMapping("/email/{email}")
    public CommonResult getUserByEmail(@PathVariable String email) {
        // 参数校验
        if (email == null || email.trim().isEmpty()) {
            return CommonResult.failed("邮箱不能为空");
        }

        User user = userService.getUserByEmail(email);
        if (user != null) {
            return CommonResult.success(user);
        }
        return CommonResult.failed("用户不存在");
    }

    /**
     * 查询所有用户
     * GET请求：/user/list/all
     *
     * @return 返回用户列表
     */
    @GetMapping("/list/all")
    public CommonResult getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return CommonResult.success(userList, "查询成功，共" + userList.size() + "条记录");
    }

    /**
     * 分页查询用户列表
     * GET请求：/user/list?pageNum=1&pageSize=10
     *
     * @param pageNum 页码（从1开始，默认为1）
     * @param pageSize 每页数量（默认为10）
     * @return 返回分页用户列表
     */
    @GetMapping("/list")
    public CommonResult getUsersByPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        // 参数校验
        if (pageNum <= 0 || pageSize <= 0) {
            return CommonResult.failed("页码和每页数量必须大于0");
        }

        if (pageSize > 100) {
            pageSize = 100;  // 限制最大每页数量为100
        }

        List<User> userList = userService.getUsersByPage(pageNum, pageSize);
        Long totalCount = userService.getUserCount();

        if (userList != null && !userList.isEmpty()) {
            return CommonResult.success(userList, "分页查询成功，总数：" + totalCount);
        }
        return CommonResult.success(userList, "没有查询到数据");
    }

    /**
     * 按状态查询用户列表
     * GET请求：/user/list/status/{status}
     *
     * @param status 用户状态（0-禁用，1-启用）
     * @return 返回指定状态的用户列表
     */
    @GetMapping("/list/status/{status}")
    public CommonResult getUsersByStatus(@PathVariable Integer status) {
        // 参数校验
        if (status == null || (status != 0 && status != 1)) {
            return CommonResult.failed("用户状态值不正确（0-禁用，1-启用）");
        }

        List<User> userList = userService.getUsersByStatus(status);
        if (userList != null && !userList.isEmpty()) {
            return CommonResult.success(userList, "查询成功，共" + userList.size() + "条记录");
        }
        return CommonResult.success(userList, "没有查询到数据");
    }

    /**
     * 修改用户信息
     * PUT请求：/user/update
     *
     * @param user 用户对象（必须包含id）
     * @return 返回操作结果
     */
    @PutMapping("/update")
    public CommonResult updateUser(@RequestBody User user) {
        // 参数校验
        if (user == null || user.getId() == null || user.getId() <= 0) {
            return CommonResult.failed("用户ID不能为空或无效");
        }

        // 检查用户是否存在
        User existingUser = userService.getUserById(user.getId());
        if (existingUser == null) {
            return CommonResult.failed("用户不存在");
        }

        // 如果修改了用户名，检查是否已被其他用户占用
        if (user.getUsername() != null && !user.getUsername().equals(existingUser.getUsername())) {
            if (userService.isUsernameExists(user.getUsername())) {
                return CommonResult.failed("用户名已存在");
            }
        }

        // 如果修改了邮箱，检查是否已被其他用户占用
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            if (userService.isEmailExists(user.getEmail())) {
                return CommonResult.failed("邮箱已存在");
            }
        }

        boolean result = userService.updateUser(user);
        if (result) {
            return CommonResult.success(user, "用户信息修改成功");
        }
        return CommonResult.failed("用户信息修改失败");
    }

    /**
     * 删除单个用户
     * DELETE请求：/user/delete/{id}
     *
     * @param id 用户ID
     * @return 返回操作结果
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult deleteUserById(@PathVariable Long id) {
        // 参数校验
        if (id == null || id <= 0) {
            return CommonResult.failed("用户ID不能为空或无效");
        }

        // 检查用户是否存在
        User user = userService.getUserById(id);
        if (user == null) {
            return CommonResult.failed("用户不存在");
        }

        boolean result = userService.deleteUserById(id);
        if (result) {
            return CommonResult.success(null, "用户删除成功");
        }
        return CommonResult.failed("用户删除失败");
    }

    /**
     * 批量删除用户
     * DELETE请求：/user/delete/batch
     * 请求体示例：["1","2","3"]
     *
     * @param ids 用户ID列表
     * @return 返回操作结果
     */
    @DeleteMapping("/delete/batch")
    public CommonResult deleteUsersByIds(@RequestBody List<Long> ids) {
        // 参数校验
        if (ids == null || ids.isEmpty()) {
            return CommonResult.failed("用户ID列表不能为空");
        }

        if (ids.size() > 100) {
            return CommonResult.failed("单次批量删除数量不能超过100");
        }

        int result = userService.deleteUsersByIds(ids);
        if (result > 0) {
            return CommonResult.success(null, "成功删除" + result + "个用户");
        }
        return CommonResult.failed("没有删除任何用户或删除失败");
    }

    /**
     * 用户登录
     * POST请求：/user/login
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回登录结果，成功返回用户信息
     */
    @PostMapping("/login")
    public CommonResult login(
            @RequestParam String username,
            @RequestParam String password) {
        // 参数校验
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            return CommonResult.failed("用户名和密码不能为空");
        }

        User user = userService.login(username, password);
        if (user != null) {
            return CommonResult.success(user, "登录成功");
        }
        return CommonResult.failed("用户名或密码错误");
    }

    /**
     * 获取用户总数
     * GET请求：/user/count
     *
     * @return 返回用户总数
     */
    @GetMapping("/count")
    public CommonResult getUserCount() {
        Long count = userService.getUserCount();
        return CommonResult.success(count, "用户总数：" + count);
    }

    /**
     * 启用用户
     * PUT请求：/user/enable/{id}
     *
     * @param id 用户ID
     * @return 返回操作结果
     */
    @PutMapping("/enable/{id}")
    public CommonResult enableUser(@PathVariable Long id) {
        // 参数校验
        if (id == null || id <= 0) {
            return CommonResult.failed("用户ID不能为空或无效");
        }

        boolean result = userService.enableUser(id);
        if (result) {
            return CommonResult.success(null, "用户已启用");
        }
        return CommonResult.failed("用户启用失败，用户不存在或操作异常");
    }

    /**
     * 禁用用户
     * PUT请求：/user/disable/{id}
     *
     * @param id 用户ID
     * @return 返回操作结果
     */
    @PutMapping("/disable/{id}")
    public CommonResult disableUser(@PathVariable Long id) {
        // 参数校验
        if (id == null || id <= 0) {
            return CommonResult.failed("用户ID不能为空或无效");
        }

        boolean result = userService.disableUser(id);
        if (result) {
            return CommonResult.success(null, "用户已禁用");
        }
        return CommonResult.failed("用户禁用失败，用户不存在或操作异常");
    }

    /**
     * 重置用户密码
     * PUT请求：/user/reset-password/{id}
     *
     * @param id 用户ID
     * @param newPassword 新密码
     * @return 返回操作结果
     */
    @PutMapping("/reset-password/{id}")
    public CommonResult resetPassword(
            @PathVariable Long id,
            @RequestParam String newPassword) {
        // 参数校验
        if (id == null || id <= 0) {
            return CommonResult.failed("用户ID不能为空或无效");
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return CommonResult.failed("新密码不能为空");
        }

        if (newPassword.length() < 6) {
            return CommonResult.failed("密码长度不能少于6位");
        }

        boolean result = userService.resetPassword(id, newPassword);
        if (result) {
            return CommonResult.success(null, "密码重置成功");
        }
        return CommonResult.failed("密码重置失败，用户不存在或操作异常");
    }
}
