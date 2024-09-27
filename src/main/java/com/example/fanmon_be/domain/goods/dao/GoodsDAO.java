package com.example.fanmon_be.domain.goods.dao;

import com.example.fanmon_be.domain.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GoodsDAO extends JpaRepository<Goods, UUID> {
}
