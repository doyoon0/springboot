package com.springboot.shoppy_fullstack_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "view_cartlist")
public class CartListView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private int cid;

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

    /* 총 금액 */
    private int totalPrice;
}
