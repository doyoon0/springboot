package com.springboot.shoppy_fullstack_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor //기본생성자 만들어주고, Setter 이용해서 주입
public class CartListResponseDto {
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
