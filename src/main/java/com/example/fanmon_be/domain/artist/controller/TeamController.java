package com.example.fanmon_be.domain.artist.controller;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.artist.service.TeamService;
import com.example.fanmon_be.domain.management.entity.Management;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Team> createTeam(HttpServletRequest request, @ModelAttribute Team team, @RequestParam("managementuuid") UUID managementuuid) {
        //프론트에서 넘어온 uuid 정보들 포함시키도록
        team.setManagement(new Management(managementuuid));

        String path = request.getServletContext().getRealPath("/resources/teamimg");
        System.out.println("real path : "+path);
        String fname = null;
        MultipartFile uploadfile = team.getUploadfile();
        fname = uploadfile.getOriginalFilename();
        if(fname!=null && !fname.equals("")) {
            try{
                FileOutputStream fos = new FileOutputStream(new File(path+"/"+fname));
                FileCopyUtils.copy(uploadfile.getInputStream(), fos);
                fos.close();
                team.setFname(fname);
            }catch (Exception e){
                System.out.println("파일 처리 예외! => "+e.getMessage());
            }
        }
        Team createdTeam = teamService.createTeam(team);
        return ResponseEntity.ok(createdTeam);
    }
}
