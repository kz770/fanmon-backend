package com.example.fanmon_be.domain.artist.service;

import com.example.fanmon_be.domain.artist.dao.TeamDAO;
import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeamService {
    @Autowired
    private TeamDAO teamDAO;

    public List<Team> findAll(){
        return teamDAO.findAll();
    }

    public Team findById(UUID uuid){
        return teamDAO.findById(uuid).get();
    }

    public List<Team> getTeamsByManagementUuid(UUID managementUuid){
        return teamDAO.findByManagementManagementuuid(managementUuid);
    }
}
