package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.KakaoPay;
import com.springboot.shoppy_fullstack_app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public int save(KakaoPay kakaoPay) {
        int rows = orderRepository.saveOrders(kakaoPay);
        if (rows != 1) {
            throw new RuntimeException("주문 저장 실패");
        }

        int rows_detail = orderRepository.saveOrderDetail(kakaoPay);
        if (rows_detail < 1) {
            throw new RuntimeException("주문 상세 저장 실패");
        }

        int rows_cart = orderRepository.deleteCartItem(kakaoPay.getCidList());
        if (rows_cart < 1) {
            System.out.println("장바구니 항목 삭제 실패 (이미 비었을 수 있음)");
        }

        return rows;
    }
}