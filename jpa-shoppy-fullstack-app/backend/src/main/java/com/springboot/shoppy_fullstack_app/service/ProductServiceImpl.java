package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.ProductDto;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfoDto;
import com.springboot.shoppy_fullstack_app.dto.ProductQnaDto;
import com.springboot.shoppy_fullstack_app.dto.ProductReturnDto;
import com.springboot.shoppy_fullstack_app.entity.Product;
import com.springboot.shoppy_fullstack_app.entity.ProductDetailinfo;
import com.springboot.shoppy_fullstack_app.entity.ProductQna;
import com.springboot.shoppy_fullstack_app.jpa_repository.JpaProductRepository;
import com.springboot.shoppy_fullstack_app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//@Transactional mysql은 auto commit 이므로 생략 가능
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final JpaProductRepository jpaProductRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              JpaProductRepository jpaProductRepository
                              )
    {
        this.productRepository = productRepository;
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> dlist = new ArrayList<>();
        List<Product> list = jpaProductRepository.findAll();
        list.forEach((Product product) -> dlist.add(new ProductDto(product)));

        return dlist;
    }

    @Override
    public ProductDto findById(int pid) {
        
        Product entity =  jpaProductRepository.findByPid(pid);
        return new ProductDto(entity); //변환하는 생성자 DTO에 만들어놨었음
    }

    @Override
    public ProductDetailinfoDto findDetailinfo(int pid) {
        ProductDetailinfo entity = jpaProductRepository.findProductDetailinfo(pid);
        return new ProductDetailinfoDto(entity);
    }

    @Override
    public List<ProductQnaDto> findQna(int pid) {
        List<ProductQnaDto> list = new ArrayList<>(); //Dto 기준으로 list를 생성
        List<ProductQna> entityList = jpaProductRepository.findQna(pid); //entity도 list 형태로 생성을 하고 pid 전달
        entityList.forEach(entity -> list.add(new ProductQnaDto(entity))); //for문 돌려서 pid에 해당하는 QnA만 가져옴
        return list;
    }

    @Override
    public ProductReturnDto findReturn() {
        return new ProductReturnDto(jpaProductRepository.findReturn());
    }
}
