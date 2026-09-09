package com.macro.common.entity;

import lombok.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *
 * 包含常见用户信息字段，与数据库 user 表对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键，用户ID */
    private Long id;

    /** 用户名 */
    private String username;

    /** 密码（已哈希） */
    private String password;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 状态：1=正常，0=禁用 */
    private Integer status;

    /** 最后登录时间 */
    private Date lastLogin;

    /** 创建时间 */
    private Date createdAt;

    /** 更新时间 */
    private Date updatedAt;
}
