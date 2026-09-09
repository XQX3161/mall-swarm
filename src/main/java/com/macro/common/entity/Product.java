package com.macro.common.entity;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体类
 *
 * 字段包括：id、name、sku、price、stock、status 等
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键，商品ID */
    private Long id;

    /** 商品名称 */
    private String name;

    /** 库存单位（SKU） */
    private String sku;

    /** 商品描述 */
    private String description;

    /** 价格（单位：元） */
    private BigDecimal price;

    /** 库存 */
    private Integer stock;

    /** 状态：1=上架，0=下架 */
    private Integer status;

    /** 创建时间 */
    private Date createdAt;

    /** 更新时间 */
    private Date updatedAt;
}
