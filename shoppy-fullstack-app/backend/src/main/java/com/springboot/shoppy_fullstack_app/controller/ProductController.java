package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.Product;
import com.springboot.shoppy_fullstack_app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired  //실제 Service 구현체(Impl)가 다른데 있으니 wire로 엮어줘야한다.
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/detailinfo")
    public String detailinfo(@RequestBody Product product) {
        System.out.println("pid: " + product.getPid());
        return "detailinfo!!";
    }

    @GetMapping("/all")
    public List<Product> all() {
        return productService.findAll();
    }

    @PostMapping("/pid")
    public Product pid(@RequestBody Product product) {
        return productService.findById(product.getPid());
    }
}
