package com.springboot.shoppy_fullstack_app.dto;

import com.springboot.shoppy_fullstack_app.entity.CartItem;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CartItemDto {
    private int cid;
    private int qty;
    private int pid;
    private String id;
    private String size;
    private LocalDate cdate;

    /* cart 아이템 수량체크 */
    private Long checkQty;
    
    /* cart 아이콘 위에 수량 +/- 여부 */
    private String type;

    /* cart 에 담긴 총 수량 */
    private int sumQty;
}
