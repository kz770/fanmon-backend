package com.example.fanmon_be.domain.board.entity;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.entity.Management;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name="boardnotice")
public class Boardnotice {

    @Id
    @Column(name = "noticeuuid", nullable = false)
    private UUID noticeuuid;

    @PrePersist
    public void generateUUID(){
        if(noticeuuid == null){
            noticeuuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="teamuuid", nullable = false)
    private Team team;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="managementuuid", nullable = false)
    private Management management;

    private String title;
    private LocalDateTime createdat;
    private String content;

}
