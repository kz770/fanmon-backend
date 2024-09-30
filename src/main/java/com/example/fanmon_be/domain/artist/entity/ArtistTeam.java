package com.example.fanmon_be.domain.artist.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name="artistteam")
public class ArtistTeam {
    @Id
    private UUID artistgroupuuid;
    @PrePersist
    protected void onCreate() {
        if (artistgroupuuid==null){
            this.artistgroupuuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="teamname", updatable = true, nullable = false)
    private Team team;


    @ManyToOne
    @JoinColumn(name="artistname", nullable = true)    //아티스트는 반드시 있어야함
    private Artist aritst;

    private String name;
}
