package com.example.fanmon_be.domain.board.service;

import com.example.fanmon_be.domain.board.dao.FancommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FanCommentService {
    @Autowired
    FancommentDAO dao;

    public int countById(UUID uuid){
        return dao.countById(uuid);
    }
}
