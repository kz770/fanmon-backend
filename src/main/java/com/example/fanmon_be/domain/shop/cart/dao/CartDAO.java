package com.example.fanmon_be.domain.shop.cart.dao;

import com.example.fanmon_be.domain.shop.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartDAO extends JpaRepository<Cart, UUID> {

}
