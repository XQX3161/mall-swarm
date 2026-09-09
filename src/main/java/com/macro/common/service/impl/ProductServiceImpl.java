package com.macro.common.service.impl;

import com.macro.common.entity.Product;
import com.macro.common.mapper.ProductMapper;
import com.macro.common.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * ProductService 实现类：完成基本的 CRUD 逻辑，并做简单参数校验
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product create(Product product) {
        Assert.notNull(product, "product 不能为空");
        Assert.hasText(product.getName(), "name 不能为空");
        if (product.getPrice() == null) product.setPrice(java.math.BigDecimal.ZERO);
        product.setStock(product.getStock() == null ? 0 : product.getStock());
        product.setStatus(product.getStatus() == null ? 1 : product.getStatus());
        Date now = new Date();
        product.setCreatedAt(now);
        product.setUpdatedAt(now);
        productMapper.insert(product);
        return product;
    }

    @Override
    public Product update(Product product) {
        Assert.notNull(product, "product 不能为空");
        Assert.notNull(product.getId(), "id 不能为空");
        product.setUpdatedAt(new Date());
        int rows = productMapper.updateById(product);
        if (rows <= 0) return null;
        return productMapper.selectById(product.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) return false;
        return productMapper.deleteById(id) > 0;
    }

    @Override
    public Product getById(Long id) {
        if (id == null) return null;
        return productMapper.selectById(id);
    }

    @Override
    public List<Product> listAll() {
        return productMapper.selectAll();
    }
}
