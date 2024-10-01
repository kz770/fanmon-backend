package com.example.fanmon_be.domain.artist.service;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.artist.entity.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArtistService {
    @Autowired
    private ArtistDAO dao;

    public List<Artist> findAll(){
        return dao.findAll();
    }

    public Artist findById(UUID artistuuid){
        return dao.findById(artistuuid).get();
    }
    public void save(Artist artist){
    }
}
