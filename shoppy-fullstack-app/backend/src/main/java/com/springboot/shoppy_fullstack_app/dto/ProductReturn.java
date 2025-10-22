package com.springboot.shoppy_fullstack_app.dto;

import lombok.Data;

@Data
public class ProductReturn {
    private int rid;
    private String title;
    private String description;
    private String list; //private List<ProductReturnList> 이런식으로 list 만을 위한 객체를 만들수도 있음
}
