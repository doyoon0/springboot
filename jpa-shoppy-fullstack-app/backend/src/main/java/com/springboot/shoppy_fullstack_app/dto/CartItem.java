package com.springboot.shoppy_fullstack_app.dto;

import lombok.Data;

@Data
public class CartItem {
    private int cid;
    private int qty;
    private int pid;
    private String id;
    private String size;
    private String cdate;

    /* cart 아이템 수량체크 */
    private int checkQty;
    
    /* cart 아이콘 위에 수량 +/- 여부 */
    private String type;

    /* cart 에 담긴 총 수량 */
    private int sumQty;
}
