package com.example.fanmon_be.domain.artist.service;

import com.example.fanmon_be.domain.artist.dao.TeamDAO;
import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.dao.ManagementDAO;
import com.example.fanmon_be.domain.management.entity.Management;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Team getTeamById(UUID teamuuid){ return teamDAO.findById(teamuuid).orElse(null);}

    public List<Team> getTeamsByManagementUuid(UUID managementUuid){
        return teamDAO.findByManagementManagementuuid(managementUuid);
    }

    public Team createTeam(Team team){ return teamDAO.save(team); }

    public Team updateTeam(UUID teamuuid,Team team){
        Management management = managementDAO.findByManagementuuid(team.getManagement().getManagementuuid()); // if (management == null) {} 나중에 예외처리
        team.setManagement(management);
        team.setTeamuuid(teamuuid); //수정하는거니까 그 아이디 그대로 넣어주기
        return teamDAO.save(team);
    }
    @Transactional
    public void deleteTeam(UUID teamuuid){
        Team team = teamDAO.findByTeamuuid(teamuuid);
        System.out.println("삭제하려는 team : "+team.toString());
        if(team != null){
            team.setManagement(null); //management 참조 해제
            teamDAO.save(team); //변경 사항 저장
            teamDAO.delete(team); //팀 삭제
        }else{
            System.out.println("삭제하려는 team 못 불러옴");
        }
    }

}
