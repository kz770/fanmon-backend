package com.example.fanmon_be.domain.board.service;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.board.dao.ArtistboardDAO;
import com.example.fanmon_be.domain.board.entity.Artistboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArtistBoardService {
    @Autowired
    ArtistboardDAO artistboardDAO;

    public List<Artistboard> findById(UUID uuid){
        return artistboardDAO.findArtistboardByTeamTeamuuidOrderByCreatedat(uuid);
    }
    public void save(Artistboard artistboard){
        artistboardDAO.save(artistboard);
    }

    public void delete(UUID uuid) {
        artistboardDAO.deleteByFanboarduuid(uuid);
    }
}
