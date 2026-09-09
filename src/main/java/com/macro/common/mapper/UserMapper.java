package com.macro.common.mapper;

import com.macro.common.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户数据访问接口
 * MyBatis Mapper接口，提供User实体的数据库操作
 *
 * @author macro
 */
@Mapper
public interface UserMapper {

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO user (username, password, email, phone, status, created_at, updated_at) " +
            "VALUES (#{username}, #{password}, #{email}, #{phone}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 批��删除用户
     *
     * @param ids 用户ID列表
     * @return 影响的行数
     */
    @Delete("<script>" +
            "DELETE FROM user WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteByIds(@Param("ids") List<Long> ids);

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     * @return 影响的行数
     */
    @Update("UPDATE user SET username = #{username}, password = #{password}, email = #{email}, " +
            "phone = #{phone}, status = #{status}, updated_at = NOW() WHERE id = #{id}")
    int update(User user);

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @Select("SELECT id, username, password, email, phone, status, created_at, updated_at FROM user WHERE id = #{id}")
    User selectById(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Select("SELECT id, username, password, email, phone, status, created_at, updated_at FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象
     */
    @Select("SELECT id, username, password, email, phone, status, created_at, updated_at FROM user WHERE email = #{email}")
    User selectByEmail(String email);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Select("SELECT id, username, password, email, phone, status, created_at, updated_at FROM user")
    List<User> selectAll();

    /**
     * 分页查询用户列表
     *
     * @param offset 偏移量
     * @param limit 每页数量
     * @return 用户列表
     */
    @Select("SELECT id, username, password, email, phone, status, created_at, updated_at FROM user LIMIT #{offset}, #{limit}")
    List<User> selectByPage(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 根据状态查询用户
     *
     * @param status 用户状态
     * @return 用户列表
     */
    @Select("SELECT id, username, password, email, phone, status, created_at, updated_at FROM user WHERE status = #{status}")
    List<User> selectByStatus(Integer status);

    /**
     * 统计用户总数
     *
     * @return 用户总数
     */
    @Select("SELECT COUNT(*) FROM user")
    Long count();

    /**
     * 统计指定状态的用户数量
     *
     * @param status 用户状态
     * @return 用户数量
     */
    @Select("SELECT COUNT(*) FROM user WHERE status = #{status}")
    Long countByStatus(Integer status);
}
