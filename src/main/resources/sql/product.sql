-- src/main/resources/sql/product.sql
-- 商品表（映射 Product 实体）

CREATE TABLE IF NOT EXISTS `product` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键，商品ID',
  `name` VARCHAR(200) NOT NULL COMMENT '商品名称',
  `sku` VARCHAR(100) DEFAULT NULL COMMENT '库存单位（SKU）',
  `description` TEXT DEFAULT NULL COMMENT '商品描述',
  `price` DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '价格（单位：元）',
  `stock` INT NOT NULL DEFAULT 0 COMMENT '库存数量',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1=上架，0=下架/禁用',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_product_name` (`name`(100)),
  UNIQUE KEY `uk_product_sku` (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表，映射 Product 实体';
