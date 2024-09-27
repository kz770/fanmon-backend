package com.example.fanmon_be.domain.shop.goods.entity;

import com.example.fanmon_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name="usergoods")
public class Usergoods {

    @Id
    @Column(name = "usergoodsuuid", nullable = false)
    private UUID usergoodsuuid;

    @PrePersist
    public void generateUUID(){
        if(usergoodsuuid == null){
            usergoodsuuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="useruuid")
    private User user;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="goodsuuid")
    private Goods goods;

    private String cardinfo;
}