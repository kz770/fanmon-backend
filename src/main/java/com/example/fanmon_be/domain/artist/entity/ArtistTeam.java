package com.example.fanmon_be.domain.artist.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name="artistteam")
public class ArtistTeam {
    @Id
    @Column(name = "artistteamuuid",nullable = false)
    private UUID artistteamuuid;
    @PrePersist
    protected void onCreate() {
        if (artistteamuuid==null){
            this.artistteamuuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="teamuuid", updatable = true)
    private Team team;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="artistuuid", nullable = true)    //아티스트는 반드시 있어야함
    private Artist artist;

}
