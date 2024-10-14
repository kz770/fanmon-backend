package com.example.fanmon_be.domain.meeting.service;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.artist.dao.TeamDAO;
import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.artist.service.ArtistService;
import com.example.fanmon_be.domain.management.dao.ManagementDAO;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.domain.meeting.dao.StayroomDAO;
import com.example.fanmon_be.domain.meeting.dao.StayuserlistDAO;
import com.example.fanmon_be.domain.meeting.entity.Stayroom;
import com.example.fanmon_be.domain.meeting.entity.Stayuserlist;
import com.example.fanmon_be.domain.meeting.enums.MeetingroomStatus;
import com.example.fanmon_be.domain.meeting.enums.UserStatusInRoom;
import com.example.fanmon_be.domain.meeting.utility.ByteToUUIDConverter;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class StayroomService {
    @Autowired
    private StayroomDAO stayroomDAO;

    @Autowired
    private StayuserlistDAO stayuserlistDAO;

    @Autowired
    private ManagementDAO managementDAO;

    @Autowired
    private TeamDAO teamDAO;

    @Autowired
    private UserDAO userDAO;

    private final ObjectMapper objectMapper;

    public StayroomService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> jsonToMap(String jsonString){
        try{
            return objectMapper.readValue(jsonString, Map.class);
        }catch(JsonProcessingException e){
            System.out.println(e);
        }
        return null;
    }

    public int maxPeopleNum(String teamName){
        return stayroomDAO.randomUserMaxNum(teamName);
    }

    public LocalDateTime mergeLocalDateTime(String date, String time){
        LocalDate localDate = LocalDate.parse(date); // "yyyy-MM-dd" 형식이어야 함
        LocalTime localTime = LocalTime.parse(time); // "HH:mm" 형식이어야 함
        return LocalDateTime.of(localDate, localTime);
    }
    public List<Team> findTeamByManagementUuid(String managementUuid){
        List<Team> r = null;
        if(managementUuid != null && !managementUuid.equals("")){
            r=  teamDAO.findByManagementManagementuuid(UUID.fromString(managementUuid));
        }else{
            System.out.println("아이디 is null");
        }

        return r;
    }
    public void insertRandomUserList(UUID stayuuid){
        List<User> list = new ArrayList<>();
        Stayroom stayroom = stayroomDAO.findById(stayuuid).get();
        int peoplenum = stayroom.getPeopleNum();
        String teamname = stayroom.getTeam().getName();

        int maxnum = stayroomDAO.randomUserMaxNum(teamname);
        int no = 1;
        System.out.println("랜덤유저 시작");
        while(no <= peoplenum && no <= maxnum){
            UUID userUuid = ByteToUUIDConverter.byteArrayToUUID(stayuserlistDAO.findRandomUserID(teamname));
            User randomUser = userDAO.findById(userUuid).get();
            if(!list.contains(randomUser)){
                Stayuserlist stayuserlist = new Stayuserlist();
                stayuserlist.setStayroom(stayroom);
                stayuserlist.setUser(randomUser);
                stayuserlist.setNo(no);
                stayuserlist.setStatus(UserStatusInRoom.ERROR_QUIT);
                list.add(randomUser);
                stayuserlistDAO.save(stayuserlist);
                System.out.println("랜덤유저"+no+" : "+stayuserlist);
                no++;
            }
        }
        System.out.println("랜덤유저 끝");
    }

    public void insertNewStayroom(MultipartFile roomImage, String stayroom){
        Map<String, Object> mapInputRoom = jsonToMap(stayroom);
        Stayroom newStayroom = new Stayroom();

        // roomImage가 null이 아닌 경우에만 byte[]로 변환
        if (roomImage != null && !roomImage.isEmpty()) {
            try {
                byte[] imageBytes = roomImage.getBytes(); // byte[]로 변환
                newStayroom.setRoomImage(imageBytes); // Entity에 설정
            } catch (IOException e) {
                e.printStackTrace(); // 예외 처리
            }
        }
        newStayroom.setManagement(managementDAO.findById(UUID.fromString(mapInputRoom.get("managementuuid").toString())).get());
        newStayroom.setTeam(teamDAO.findById(UUID.fromString(mapInputRoom.get("teamUUID").toString())).get());
        newStayroom.setName(mapInputRoom.get("name").toString());
        newStayroom.setCreatedat(LocalDateTime.now());
        newStayroom.setPublic((Boolean) mapInputRoom.get("isPublic"));
        newStayroom.setMeetingTime(Integer.parseInt(mapInputRoom.get("meetingTime").toString()));
        newStayroom.setPeopleNum(Integer.parseInt(mapInputRoom.get("peopleNum").toString()));
        newStayroom.setRestTime(Integer.parseInt(mapInputRoom.get("restTime").toString()));
        newStayroom.setMeetingstartedat(
                mergeLocalDateTime(
                        mapInputRoom.get("meetingDate").toString(),
                        mapInputRoom.get("meetingStartTime").toString()
                )
        );
        Stayroom saveRoom = stayroomDAO.save(newStayroom);
        insertRandomUserList(saveRoom.getStayuuid());
    }

    public Stayroom findById(String uuid){
        return stayroomDAO.findById(UUID.fromString(uuid)).get();
    }
    public List<Stayuserlist> findAllUserList(String stayuuid){
        return stayuserlistDAO.findByStayroom_StayuuidOrderByNo(UUID.fromString(stayuuid));
    }

    public List<Stayroom> findAll(){
        return stayroomDAO.findAll();
    }

    public List<Stayroom> findAll(String uuid){
        return stayroomDAO.findAll();
    }
}
