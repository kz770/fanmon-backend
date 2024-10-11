package com.example.fanmon_be.domain.shop.buy.service;

import com.example.fanmon_be.domain.shop.buy.dao.CardinfoDAO;
import com.example.fanmon_be.domain.shop.buy.entity.Cardinfo;
import com.example.fanmon_be.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardinfoService {

    @Autowired
    private CardinfoDAO cardinfoDAO;
}
