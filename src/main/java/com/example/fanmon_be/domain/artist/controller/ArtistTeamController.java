package com.example.fanmon_be.domain.artist.controller;

import com.example.fanmon_be.domain.artist.entity.ArtistTeam;
import com.example.fanmon_be.domain.artist.service.ArtistTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/management/artistTeam")
public class ArtistTeamController {
    @Autowired
    private ArtistTeamService artistTeamService;

    //Team - Artist 관계 생성
    @PostMapping
    public ResponseEntity<ArtistTeam> create(
            @RequestParam("teamuuid") UUID teamuuid,
            @RequestParam("artistuuid") UUID artistuuid) {
        System.out.println(artistuuid);
        System.out.println(teamuuid);
        ArtistTeam createdAT = artistTeamService.createArtistTeam(teamuuid,artistuuid);
        return ResponseEntity.ok(createdAT);
    }
}
