package com.example.fanmon_be.domain.management.service;

import com.example.fanmon_be.domain.management.dao.ManagementDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
    @Autowired
    private ManagementDAO dao;
}
