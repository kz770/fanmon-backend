package com.example.fanmon_be.domain.meeting.entity;

import com.example.fanmon_be.domain.meeting.enums.UserStatusInRoom;
import com.example.fanmon_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name="stayuserlist")
@Data
public class Stayuserlist {
    @Id
    private UUID stayuserlistuuid;

    @PrePersist
    public void generateUUID(){
        if(stayuserlistuuid == null){
            stayuserlistuuid = UUID.randomUUID();
        }
    }

    @ManyToOne
    @JoinColumn(name="stayuuid")
    private Stayroom stayroom;


    @ManyToOne
    @JoinColumn(name="useruuid")
    private User user;

    private int no;

    @Enumerated(EnumType.STRING)
    private UserStatusInRoom status = UserStatusInRoom.ERROR_QUIT;

    private String signature;
}
