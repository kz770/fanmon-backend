package com.example.fanmon_be.domain.artist.controller;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.artist.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/{managementuuid}")
    public ResponseEntity<List<Team>> getTeams(@PathVariable UUID managementuuid) {
        List<Team> teamList = teamService.getTeamsByManagementUuid(managementuuid);
        System.out.println("teamList : "+teamList);
        return ResponseEntity.ok(teamList);
    }
}
