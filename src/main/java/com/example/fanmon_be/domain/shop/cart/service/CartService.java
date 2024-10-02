package com.example.fanmon_be.domain.shop.cart.service;

import com.example.fanmon_be.domain.shop.cart.dao.CartDAO;
import com.example.fanmon_be.domain.shop.cart.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    // 장바구니 목록 출력
    public List<Cart> findById(UUID uuid) {
        return cartDAO.findById(uuid);
    }
}
