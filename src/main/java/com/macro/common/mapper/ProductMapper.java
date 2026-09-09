package com.macro.common.mapper;

import com.macro.common.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ProductMapper 接口，定义对 product 表的 CRUD 操作
 */
@Mapper
public interface ProductMapper {

    int insert(Product product);

    int updateById(Product product);

    int deleteById(@Param("id") Long id);

    Product selectById(@Param("id") Long id);

    List<Product> selectAll();
}
