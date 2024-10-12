package com.example.fanmon_be.domain.board.entity;

import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name="artistboard")
public class Artistboard extends BaseEntity {
    @Id
    @Column(name = "artistboarduuid", nullable = false)
    private UUID artistboarduuid;

    @PrePersist
    public void generateBaseColumns(){
        if(artistboarduuid == null){
            artistboarduuid = UUID.randomUUID();
        }
        this.createdat =LocalDateTime.now();
        this.updatedat = LocalDateTime.now();
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="teamuuid", nullable = false)
    private Team team;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="artistuuid", nullable = false)
    private Artist artist;

    private LocalDateTime createdat;
    private LocalDateTime updatedat;
    private  String fname;
    private String content;
    private long likecount;
}
