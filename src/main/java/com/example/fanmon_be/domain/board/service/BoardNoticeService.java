package com.example.fanmon_be.domain.board.service;

import com.example.fanmon_be.domain.board.dao.BoardnoticeDAO;
import com.example.fanmon_be.domain.board.entity.Boardnotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardNoticeService {
    @Autowired
    BoardnoticeDAO dao;
    public List<Boardnotice> findAll(){
        return dao.findAll();
    }
}
