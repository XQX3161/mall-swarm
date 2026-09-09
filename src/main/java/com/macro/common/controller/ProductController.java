package com.macro.common.controller;

import com.macro.common.entity.Product;
import com.macro.common.dto.Result;
import com.macro.common.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品 REST 控制器
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Result<Product> create(@RequestBody Product product) {
        Product created = productService.create(product);
        return Result.ok(created);
    }

    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.ok(product);
    }

    @PutMapping
    public Result<Product> update(@RequestBody Product product) {
        Product updated = productService.update(product);
        return Result.ok(updated);
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean ok = productService.deleteById(id);
        return Result.ok(ok);
    }

    @GetMapping
    public Result<List<Product>> listAll() {
        return Result.ok(productService.listAll());
    }
}
