package com.example.fanmon_be.domain.board.service;

import com.example.fanmon_be.domain.board.dao.BoardnoticeDAO;
import com.example.fanmon_be.domain.board.entity.Boardnotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class BoardNoticeService {
    @Autowired
    BoardnoticeDAO dao;
    public List<Boardnotice> findById(UUID uuid){
        return dao.findBoardnoticeByTeamTeamuuid(uuid);
    }
}
