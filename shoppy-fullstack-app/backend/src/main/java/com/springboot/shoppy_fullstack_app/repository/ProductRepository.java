package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Product;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfo;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(int pid);
    ProductDetailinfo findDetailinfo(int pid);
}
