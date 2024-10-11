package com.example.fanmon_be.domain.shop.buy.service;

import com.example.fanmon_be.domain.shop.buy.dao.OrdersDAO;
import com.example.fanmon_be.domain.shop.buy.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    @Autowired
    OrdersDAO ordersDAO;

    public void save(Orders orders){
        ordersDAO.save(orders);
    };
}
