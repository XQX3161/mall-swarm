package com.macro.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品实体类
 *
 * 字段对应：
 * - id: 主键
 * - name: 商品名称
 * - price: 价格
 * - stock: 库存
 * - status: 状态（1=正常，0=下架/禁用）
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键，商品ID */
    private Long id;

    /** 商品名称 */
    private String name;

    /** 价格 */
    private BigDecimal price;

    /** 库存 */
    private Integer stock;

    /** 状态：1=正常，0=下架/禁用 */
    private Integer status;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                '}';
    }
}
