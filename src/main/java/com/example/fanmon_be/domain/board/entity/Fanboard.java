package com.example.fanmon_be.domain.board.entity;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name="fanboard")
public class Fanboard {
    @Id
    @Column(name = "fanboarduuid", nullable = false)
    private UUID fanboarduuid;

    @PrePersist
    public void generateBaseColumns(){
        if(fanboarduuid == null){
            fanboarduuid = UUID.randomUUID();
        }
        this.createdat =LocalDateTime.now();
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="teamuuid", nullable = false)
    private Team team;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="useruuid", nullable = false)
    private User user;

    private String title;
    private LocalDateTime createdat;
    private String content;
    private long likecount;
}
