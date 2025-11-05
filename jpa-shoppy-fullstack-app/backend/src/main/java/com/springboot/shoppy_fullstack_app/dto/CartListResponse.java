package com.springboot.shoppy_fullstack_app.dto;

import lombok.Data;

@Data
public class CartListResponse {
    private String id;
    private int pid;
    private String mname;
    private String phone;
    private String email;
    private String info;
    private String name;
    private String image;
    private int price;
    private String size;
    private int qty;
    private int cid;

    /* 총 금액 */
    private int totalPrice;
}
