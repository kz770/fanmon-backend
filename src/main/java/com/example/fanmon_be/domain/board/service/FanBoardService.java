package com.example.fanmon_be.domain.board.service;

import com.example.fanmon_be.domain.board.dao.FanboardDAO;
import com.example.fanmon_be.domain.board.entity.Fanboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FanBoardService {
    @Autowired
    FanboardDAO dao;
    public List<Fanboard> findById(UUID uuid){
        return dao.findFanboardByTeamTeamuuid(uuid);
    }
}
