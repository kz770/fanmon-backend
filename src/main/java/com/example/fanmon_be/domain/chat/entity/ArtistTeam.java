package com.example.fanmon_be.domain.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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

//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name="groupartistname", updatable = true, nullable = false)
//    private Group group;


//    @ManyToOne
//    @JoinColumn(name="artistname", nullable = true)    //아티스트는 반드시 있어야함
//    private Artist aritst;

    private String name;
}
