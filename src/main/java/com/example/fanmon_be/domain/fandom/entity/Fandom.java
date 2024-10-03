package com.example.fanmon_be.domain.fandom.entity;

import com.example.fanmon_be.domain.artist.entity.Team;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name="fandom")
@Data
public class Fandom {
    @Id
    @Column(name="fandomuuid", nullable= false)
    private UUID fandomuuid;

    @PrePersist
    public void generateUUID(){
        if(fandomuuid == null){
            fandomuuid = UUID.randomUUID();
        }
    }

    @Column(name="name", nullable=false)
    private String name;

    @ManyToOne
    @JoinColumn(name="teamuuid")
    private Team team;

    // teamuuid, teamname 은 Team.name 이런 식으로 가져오는 걸로
}
