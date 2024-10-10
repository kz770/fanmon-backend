package com.example.fanmon_be.domain.artist.service;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.artist.dao.ArtistTeamDAO;
import com.example.fanmon_be.domain.artist.dao.TeamDAO;
import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.artist.entity.ArtistTeam;
import com.example.fanmon_be.domain.artist.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void deleteArtistTeam(UUID teamuuid) {
        System.out.println("service 삭제하려는 관계 teamuuid: " + teamuuid);
        List<ArtistTeam> listToDelete = getArtistTeamByTeamuuid(teamuuid); // 삭제하려는 관계들

        if (listToDelete.isEmpty()) {
            System.out.println("삭제할 관계가 없습니다.");
            return; // 삭제할 관계가 없으면 메서드 종료
        }

        for (ArtistTeam artistTeam : listToDelete) {
            try {
                System.out.println("삭제할 ArtistTeam: " + artistTeam);
                artistTeamDAO.delete(artistTeam);
            } catch (DataIntegrityViolationException e) {
                System.err.println("데이터 무결성 오류: " + e.getMessage());
                // 추가적인 처리(예: 사용자에게 오류 메시지 전달)
            } catch (Exception e) {
                System.err.println("삭제 중 오류 발생: " + e.getMessage());
                // 추가적인 처리(예: 사용자에게 오류 메시지 전달)
            }
        }
    }
}
