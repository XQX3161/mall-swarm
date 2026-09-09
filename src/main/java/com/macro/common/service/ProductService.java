package com.macro.common.service;

import com.macro.common.entity.Product;

import java.util.List;

/**
 * ProductService 接口，定义商品相关业务操作
 */
public interface ProductService {

    Product create(Product product);

    Product update(Product product);

    boolean deleteById(Long id);

    Product getById(Long id);

    List<Product> listAll();
}
