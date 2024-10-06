package com.example.fanmon_be.domain.shop.cart.service;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.shop.cart.dao.CartDAO;
import com.example.fanmon_be.domain.shop.cart.entity.Cart;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private UserDAO userDAO;

    // 장바구니 목록 출력
//    public List<Cart> findAll() {
//        return cartDAO.findAll();
//    }

    public List<Cart> findByUser(UUID useruuid) {

        User user = userDAO.findById(useruuid).orElse(null);
        if(useruuid != null) {
            return cartDAO.findByUser(user);
        }
        return Collections.emptyList();
    }


}
