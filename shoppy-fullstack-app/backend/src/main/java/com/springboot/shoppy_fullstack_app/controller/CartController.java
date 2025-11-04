package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.CartItem;
import com.springboot.shoppy_fullstack_app.dto.CartListResponse;
import com.springboot.shoppy_fullstack_app.dto.KakaoPay;
import com.springboot.shoppy_fullstack_app.service.CartService;
import com.springboot.shoppy_fullstack_app.service.KakaoPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    private KakaoPayService kakaoPayService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
        this.kakaoPayService = kakaoPayService;
    }

    @PostMapping("/deleteItem")
    public int deleteItem(@RequestBody CartItem cartItem) {
        return cartService.deleteItem(cartItem);
    }

    @PostMapping("/list")
    public List<CartListResponse> findList(@RequestBody CartItem cartItem,
                                   HttpServletRequest request ) {
//        HttpSession session = request.getSession(false); //기존에 생성된 세션 가져오기
//        String sid = (String)session.getAttribute("sid");
//        String ssid = session.getId();
//        ResponseEntity<?> response = null;
//
//        if(ssid != null && sid != null) {
//            System.out.println("ssid :: " + ssid + " sid :: " + sid);
//            List<CartListResponse> list = cartService.findList(cartItem);
//            response = ResponseEntity.ok(list);
//        } else {
//            response = ResponseEntity.ok(Map.of("message", false)); //security config, react에서 두번 막기때문에 의미는 업음
//        }
        return cartService.findList(cartItem);
    }

    @PostMapping("/count")
    public CartItem count(@RequestBody CartItem cartItem) {
        return cartService.getCount(cartItem);
    }

    @PostMapping("/updateQty")
    public int  updateQty(@RequestBody CartItem cartItem) {
        System.out.println("updateQty :: " + cartItem);
        return cartService.updateQty(cartItem);
    }

    @PostMapping("/checkQty")
    public CartItem checkQty(@RequestBody CartItem cartItem) {
        System.out.println("checkQty" + cartItem.getPid() + cartItem.getSize() + cartItem.getId());
        return cartService.checkQty(cartItem);
    }

    @PostMapping("/add")
    public int add(@RequestBody CartItem cartItem) {
        return cartService.add(cartItem);
    }
}
