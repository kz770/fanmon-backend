package com.example.fanmon_be.domain.shop.buy.dao;

import com.example.fanmon_be.domain.shop.buy.entity.Ordersdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdersdetailDAO extends JpaRepository<Ordersdetail, UUID> {
}
