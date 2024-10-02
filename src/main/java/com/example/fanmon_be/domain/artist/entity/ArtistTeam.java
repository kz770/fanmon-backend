package com.example.fanmon_be.domain.artist.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name="artistteam")
public class ArtistTeam {
    @Id
    private UUID artistteamuuid;
    @PrePersist
    protected void onCreate() {
        if (artistteamuuid==null){
            this.artistteamuuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="teamuuid", updatable = true, nullable = false)
    private Team team;


    @ManyToOne
    @JoinColumn(name="artistuuid", nullable = true)    //아티스트는 반드시 있어야함
    private Artist aritst;

}
