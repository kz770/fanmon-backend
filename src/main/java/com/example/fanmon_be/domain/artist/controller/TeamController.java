package com.example.fanmon_be.domain.artist.controller;

import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.artist.service.TeamService;
import com.example.fanmon_be.domain.management.entity.Management;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    //전체 team 리스트
    @GetMapping("/list")
    public ResponseEntity<List<Team>> list() {
        List<Team> list = teamService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //management의 team리스트
    @GetMapping("/list/{managementuuid}")
    public ResponseEntity<List<Team>> getTeams(@PathVariable UUID managementuuid) {
        List<Team> teamList = teamService.getTeamsByManagementUuid(managementuuid);
        System.out.println("teamList : " + teamList);
        return ResponseEntity.ok(teamList);
    }

    //팀 생성 CREATE
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Team> createTeam(HttpServletRequest request,
                                           @ModelAttribute Team team,
                                           @RequestParam("managementuuid") UUID managementuuid) {
        //프론트에서 넘어온 uuid 정보들 포함시키도록
        team.setManagement(new Management(managementuuid));

        String path = request.getServletContext().getRealPath("/resources/teamimg");
        System.out.println("real path : " + path);
        String fname = null;
        MultipartFile uploadfile = team.getUploadfile();
        fname = uploadfile.getOriginalFilename();
        if (fname != null && !fname.equals("")) {
            try {
                FileOutputStream fos = new FileOutputStream(new File(path + "/" + fname));
                FileCopyUtils.copy(uploadfile.getInputStream(), fos);
                fos.close();
                team.setFname(fname);
            } catch (Exception e) {
                System.out.println("파일 처리 예외! => " + e.getMessage());
            }
        }
        Team createdTeam = teamService.createTeam(team);
        return ResponseEntity.ok(createdTeam);
    }

    //팀 수정 UPDATE
    @PutMapping("/{teamuuid}")
    public ResponseEntity<Team> updateTeam(
            @PathVariable UUID teamuuid,
            HttpServletRequest request,
            @ModelAttribute Team team,
            @RequestParam("managementuuid") UUID managementuuid
    ){
        //프론트에서 넘어온 uuid 정보들 포함시키도록
        System.out.println("수정할 팀의 managementuuid : "+managementuuid);
        team.setManagement(new Management(managementuuid));
        //업뎃된 파일 있으면 그걸로 fname set해서 db에 저장
        String oldFname = team.getFname();
        MultipartFile uploadfile = team.getUploadfile();
        String fname = null;
        if(uploadfile != null){
            fname = uploadfile.getOriginalFilename();
            team.setFname(fname);
        }
        Team updatedTeam = teamService.updateTeam(teamuuid, team);

        //파일 관리
        if(updatedTeam!=null && fname!=null && !fname.equals("")) {
            String path = request.getServletContext().getRealPath("/resources/teamimg");
            //원래 파일 삭제
            File file = new File(path+"/"+oldFname);
            file.delete();
            try{
                //업뎃된 파일 저장
                byte[] data = uploadfile.getBytes();
                FileOutputStream fos = new FileOutputStream(path+"/"+fname);
                fos.write(data);
                fos.close();
            }catch (Exception e){
                System.out.println("파일 업로드 예외 : "+e.getMessage());
            }
        }
        if(updatedTeam==null){
            System.out.println("파일 업로드 실패");
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedTeam);
    }

    //팀 디테일
    @GetMapping("/{teamuuid}")
    public ResponseEntity<Team> getTeam(@PathVariable UUID teamuuid) {
        System.out.println("(Detail)찾는 teamuuid : " + teamuuid);
        Team team = teamService.getTeamById(teamuuid);
        if(team==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(team);
    }

    //팀 삭제
    @DeleteMapping("/{teamuuid}")
    public ResponseEntity<Team> deleteTeam(@PathVariable UUID teamuuid, HttpServletRequest request) {
        String path = request.getServletContext().getRealPath("/resources/teamimg");
        Team deleteTeam = teamService.getTeamById(teamuuid);
        String oldFname = deleteTeam.getFname();
        File file = new File(path+"/"+oldFname);
        file.delete();
        teamService.deleteTeam(teamuuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    팀 follower 수 별로 order by
    @GetMapping("/followerorderby/{managementuuid}")
    public ResponseEntity<List<Team>> getTeamsByOrder(@PathVariable UUID managementuuid) {
        List<Team> list = teamService.orderTeamByFollowers(managementuuid);
        return ResponseEntity.ok(list);
    }

    //management 별 팀 개수 COUNT
    @GetMapping("/count/{managementuuid}")
    public ResponseEntity<Long> getTeamCount(@PathVariable UUID managementuuid) {
        Long count = teamService.countByManagementUuid(managementuuid);
        if (count == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }
}
