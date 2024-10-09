package com.example.fanmon_be.domain.artist.service;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.artist.dao.ArtistTeamDAO;
import com.example.fanmon_be.domain.artist.dao.TeamDAO;
import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.artist.entity.ArtistTeam;
import com.example.fanmon_be.domain.artist.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        artistTeam.setArtist(artist);
        return artistTeamDAO.save(artistTeam);
    }

    public List<ArtistTeam> getArtistTeamByTeamuuid(UUID teamuuid) {
        List<ArtistTeam> list = artistTeamDAO.findByTeamTeamuuid(teamuuid);
        return list;
    }

    public void deleteArtistTeam(UUID teamuuid) {
        System.out.println("삭제하려는 관계 teamuuid:  " + teamuuid);
        List<ArtistTeam> listToDelete = getArtistTeamByTeamuuid(teamuuid); //삭제하려는 관계들
        for (ArtistTeam artistTeam : listToDelete) { //하나씩 참조 해제하고
            artistTeam.setArtist(null);
            artistTeamDAO.save(artistTeam); //변경사항 저장
            artistTeamDAO.delete(artistTeam); //삭제
        }
    }
}
