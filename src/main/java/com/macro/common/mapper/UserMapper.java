package com.macro.common.mapper;

import com.macro.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserMapper 接口，定义对 user 表的 CRUD 操作
 */
@Mapper
public interface UserMapper {

    int insert(User user);

    int updateById(User user);

    int deleteById(@Param("id") Long id);

    User selectById(@Param("id") Long id);

    User selectByUsername(@Param("username") String username);

    List<User> selectAll();
}
