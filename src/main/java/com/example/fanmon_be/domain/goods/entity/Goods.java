package com.example.fanmon_be.domain.goods.entity;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.entity.Management;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name="goods")
public class Goods {

    @Id
    @Column(name = "goodsuuid", nullable = false)
    private UUID goodsuuid;

    @PrePersist
    public void generateUUID(){
        if(goodsuuid == null){
            goodsuuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="managementuuid", nullable = false)
    private Management management;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="teamuuid", nullable = false)
    private Team team;

    private String category;
    private String name;
    private long qty;
    private long price;
    private String fname;
}
