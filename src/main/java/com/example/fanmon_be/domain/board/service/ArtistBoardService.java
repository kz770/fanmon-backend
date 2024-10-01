package com.example.fanmon_be.domain.board.service;

import com.example.fanmon_be.domain.board.dao.ArtistboardDAO;
import com.example.fanmon_be.domain.board.entity.Artistboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistBoardService {
    @Autowired
    ArtistboardDAO dao;
    public List<Artistboard> findAll(){
        return dao.findAll();
    }
}
