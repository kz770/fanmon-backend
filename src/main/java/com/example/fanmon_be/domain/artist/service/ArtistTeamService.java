package com.example.fanmon_be.domain.artist.service;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.artist.dao.ArtistTeamDAO;
import com.example.fanmon_be.domain.artist.dao.TeamDAO;
import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.artist.entity.ArtistTeam;
import com.example.fanmon_be.domain.artist.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArtistTeamService {
    @Autowired
    private ArtistTeamDAO artistTeamDAO;
    @Autowired
    private ArtistDAO artistDAO;
    @Autowired
    private TeamDAO teamDAO;

    public ArtistTeam createArtistTeam(UUID teamuuid, UUID artistuuid) {
        Team team = teamDAO.findByTeamuuid(teamuuid);
        Artist artist = artistDAO.findByArtistuuid(artistuuid);
        ArtistTeam artistTeam = new ArtistTeam();
        artistTeam.setTeam(team);
        artistTeam.setAritst(artist);
        return artistTeamDAO.save(artistTeam);
    }
}
