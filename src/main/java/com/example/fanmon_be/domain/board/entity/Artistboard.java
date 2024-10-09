package com.example.fanmon_be.domain.board.entity;

import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.artist.entity.Team;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name="artistboard")
public class Artistboard {
    @Id
    @Column(name = "artistboarduuid", nullable = false)
    private UUID artistboarduuid;

    @PrePersist
    public void generateUUID(){
        if(artistboarduuid == null){
            artistboarduuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name="teamuuid", nullable = false)
    private Team team;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="artistuuid", nullable = false)
    private Artist artist;

    private LocalDateTime createdat;
    private String content;
    private long likecount;
}
