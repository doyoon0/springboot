package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.ProductDto;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfoDto;
import com.springboot.shoppy_fullstack_app.dto.ProductQnaDto;
import com.springboot.shoppy_fullstack_app.dto.ProductReturnDto;

import java.util.List;

public interface ProductRepository {
    List<ProductDto> findAll();
    ProductDto findById(int pid);
    ProductDetailinfoDto findDetailinfo(int pid);
    List<ProductQnaDto> findQna(int pid);
    ProductReturnDto findReturn();
}
