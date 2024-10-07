package com.example.fanmon_be.domain.shop.buy.dao;

import com.example.fanmon_be.domain.shop.buy.entity.Cardinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardinfoDAO extends JpaRepository<Cardinfo, UUID>{
}
