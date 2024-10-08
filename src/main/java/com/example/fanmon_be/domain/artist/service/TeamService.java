package com.example.fanmon_be.domain.artist.service;

import com.example.fanmon_be.domain.artist.dao.TeamDAO;
import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.dao.ManagementDAO;
import com.example.fanmon_be.domain.management.entity.Management;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeamService {
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private ManagementDAO managementDAO;

    public List<Team> findAll(){
        return teamDAO.findAll();
    }

    public Team findById(UUID uuid){
        return teamDAO.findById(uuid).get();
    }

    public List<Team> getTeamsByManagementUuid(UUID managementUuid){
        return teamDAO.findByManagementManagementuuid(managementUuid);
    }

    public Team createTeam(Team team){
        //Management 객체 조회
        Management management = managementDAO.findByManagementuuid(team.getManagement().getManagementuuid()); // if (management == null) {} 나중에 예외처리
        team.setManagement(management);
        return teamDAO.save(team);
    }
}
