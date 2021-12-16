package com.example.service;

import java.util.List;

import com.example.dto.CartDto;
import com.example.entity.Cart;

import org.springframework.stereotype.Service;

@Service
public interface CartService {

    // 장바구니 추가
    public int CartInsert(CartDto cart);

    // 장바구니 목록
    public List<Cart> selectCart(String id);

    // 장바구니 삭제
    public int deleteCart(String id);

    // 장바구니 수정
    public int updateCart(Cart cart);
}
