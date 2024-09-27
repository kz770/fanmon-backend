package com.example.fanmon_be.domain.shop.dao;

import com.example.fanmon_be.domain.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDAO extends JpaRepository<Cart, Long> {
}
