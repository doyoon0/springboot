package com.springboot.shoppy_fullstack_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
    @Id //pk로 인식하기 위해서
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "price", columnDefinition = "MEDIUMTEXT")
    private long price;

    @Column(name = "info", length = 200)
    private String info;

    @Column(name = "rate")
    private double rate;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "img_list", columnDefinition = "JSON")
    private String imgList;
}
