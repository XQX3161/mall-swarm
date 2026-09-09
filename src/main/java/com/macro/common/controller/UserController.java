package com.macro.common.controller;

import com.macro.common.entity.User;
import com.macro.common.dto.Result;
import com.macro.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户 REST 控制器
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Result<User> create(@RequestBody User user) {
        User created = userService.create(user);
        return Result.ok(created);
    }

    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.ok(user);
    }

    @PutMapping
    public Result<User> update(@RequestBody User user) {
        User updated = userService.update(user);
        return Result.ok(updated);
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean ok = userService.deleteById(id);
        return Result.ok(ok);
    }

    @GetMapping
    public Result<List<User>> listAll() {
        return Result.ok(userService.listAll());
    }
}
