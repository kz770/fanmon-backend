package com.example.fanmon_be.domain.artist.service;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.management.dao.ManagementDAO;
import com.example.fanmon_be.domain.management.entity.Management;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArtistService {
    @Autowired
    private ArtistDAO dao;
    @Autowired
    private ManagementDAO managementDAO;

    public List<Artist> findAll(){
        return dao.findAll();
    }

    public List<Artist> getArtistsByManagementuuid(UUID managementuuid){ return dao.findArtistsByManagementManagementuuid(managementuuid);}

    public Artist create(Artist artist){ return dao.save(artist);}

    public Artist getArtistById(UUID artistuuid){ return dao.findById(artistuuid).orElse(null);}

    public Artist updateArtist(UUID artistuuid, Artist artist){
        Management management = managementDAO.findByManagementuuid(artist.getManagement().getManagementuuid());
        artist.setManagement(management);
        artist.setArtistuuid(artistuuid);
        return dao.save(artist);
    }
}
