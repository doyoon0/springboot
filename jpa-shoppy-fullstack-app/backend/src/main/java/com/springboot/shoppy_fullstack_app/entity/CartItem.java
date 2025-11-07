package com.springboot.shoppy_fullstack_app.entity;

import com.springboot.shoppy_fullstack_app.dto.CartItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cart")
@Setter
@Getter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    private int qty;
    private int pid;
    private String id;
    private String size;
    private LocalDate cdate;

    public CartItem() {}
    public CartItem(CartItemDto dto) {
        this.cid = dto.getCid();
        this.qty = dto.getQty();
        this.pid = dto.getPid();
        this.id = dto.getId();
        this.size = dto.getSize();
        this.cdate = LocalDate.now();
    }
}
