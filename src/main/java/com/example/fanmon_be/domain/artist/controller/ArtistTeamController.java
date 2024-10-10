package com.example.fanmon_be.domain.artist.controller;

import com.example.fanmon_be.domain.artist.entity.ArtistTeam;
import com.example.fanmon_be.domain.artist.service.ArtistTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management/artistTeam")
public class ArtistTeamController {
    @Autowired
    private ArtistTeamService artistTeamService;

    //Team - Artist 관계 생성 CREATE
    @PostMapping
    public ResponseEntity<ArtistTeam> create(
            @RequestParam("teamuuid") UUID teamuuid,
            @RequestParam("artistuuid") UUID artistuuid) {
        System.out.println(artistuuid);
        System.out.println(teamuuid);
        ArtistTeam createdAT = artistTeamService.createArtistTeam(teamuuid,artistuuid);
        return ResponseEntity.ok(createdAT);
    }

    //team에 속한 아티스트 목록 전달
    @GetMapping("/{teamuuid}")
    public ResponseEntity<List<ArtistTeam>> get(@PathVariable("teamuuid") UUID teamuuid) {
        List<ArtistTeam> list = artistTeamService.getArtistTeamByTeamuuid(teamuuid);
        System.out.println(list);
        return ResponseEntity.ok(list);
    }

    //Team -Artist 관계 삭제 DELETE
    @DeleteMapping("/{teamuuid}")
    public ResponseEntity<ArtistTeam> delete(@PathVariable("teamuuid") UUID teamuuid) {
        artistTeamService.deleteArtistTeam(teamuuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
