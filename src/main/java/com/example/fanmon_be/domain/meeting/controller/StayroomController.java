package com.example.fanmon_be.domain.meeting.controller;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.meeting.entity.Stayroom;
import com.example.fanmon_be.domain.meeting.entity.Stayuserlist;
import com.example.fanmon_be.domain.meeting.service.StayroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/meetingroom")
public class StayroomController {
    @Autowired
    private StayroomService stayroomService;

    @GetMapping("/teamlist")
    public List<Team> findTeamByManagementUuid(@RequestParam("managerUUID") String managerUUID){
        return stayroomService.findTeamByManagementUuid(managerUUID);
    }

    @GetMapping("/maxPeopleNum")
    public int findMaxPeopleNum(String teamName){
        return stayroomService.maxPeopleNum(teamName);
    }

    @GetMapping("/stayroom/list")
    public List<Stayroom> findAll(
        @RequestParam(name="managementUUID", required = false) String managementUUID,
        @RequestParam(name="userUUID", required = false) String userUUID
    ){
        List<Stayroom> list = null;
        if(managementUUID != null && !managementUUID.isEmpty()){
            list =  stayroomService.findAll(managementUUID);
        }else if(userUUID != null && !userUUID.isEmpty()){
            list =  stayroomService.findAll(userUUID);
        }else{
            list = stayroomService.findAll();
        }
        return list;
    }


    @PostMapping("/insert")
    public void insert(
            @RequestPart(value="roomImage", required = false) MultipartFile roomImage,
            @RequestPart("stayroom") String stayroom
    )
    {
        stayroomService.insertNewStayroom(roomImage, stayroom);
    }

    @GetMapping("/stayroom/{stayuuid}")
    public Stayroom getStayRoomDetails(@PathVariable String stayuuid) {
        return stayroomService.findById(stayuuid);
    }

    @GetMapping("/stayroom/userlist/{stayuuid}")
    public List<Stayuserlist> getUserlist(@PathVariable String stayuuid) {
        List<Stayuserlist> list = stayroomService.findAllUserList(stayuuid);
        return list;
    }

    @GetMapping("/meetingroom/{stayuuid}")
    public Stayroom getMeetingRoom(@PathVariable String stayuuid) {
        return stayroomService.findById(stayuuid);
    }
}