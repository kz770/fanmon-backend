package com.example.fanmon_be.domain.board.entity;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name="boardnotice")
public class Boardnotice extends BaseEntity {

    @Id
    @Column(name = "noticeuuid", nullable = false)
    private UUID noticeuuid;

    @PrePersist
    public void generateBaseColumns(){
        if(noticeuuid == null){
            noticeuuid = UUID.randomUUID();
        }
        super.createdat =LocalDateTime.now();
        super.updatedat = LocalDateTime.now();
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="teamuuid", nullable = false)
    private Team team;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="managementuuid", nullable = false)
    private Management management;

    private String title;
    private LocalDateTime createdat;
    private String content;

}
