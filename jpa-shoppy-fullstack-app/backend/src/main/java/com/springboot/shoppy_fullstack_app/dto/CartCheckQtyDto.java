package com.springboot.shoppy_fullstack_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter @AllArgsConstructor //2번 방법. (AllArgs..가 생성자를 만들어주는 역할)
public class CartCheckQtyDto {
    private int cid;
    private Long count; //만들어지는 컬럼은 보통 기본타입을 사용함. checkQty 대신 count 사용.

    //클래스의 필드(위에 cid, count) 데이터를 객체에 주입(Injection) 방법 2가지
    //1. 생성자
    //2. 기본생성자 + Setter 메소드
//    public CartCheckQtyDto() {}
//    public CartCheckQtyDto(int cid, Long count) {
//        this.cid = cid;
//        this.count = count;
//    }//1번 생성자를 통해 주입하는 방법 선택
}
