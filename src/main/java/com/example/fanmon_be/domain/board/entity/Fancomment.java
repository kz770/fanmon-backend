package com.example.fanmon_be.domain.board.entity;

import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name="fancomment")
public class Fancomment extends BaseEntity {
    @Id
    @Column(name = "fancommentuuid", nullable = false)
    private UUID fancommentuuid;

    @PrePersist
    public void generateUUID(){
        if(fancommentuuid == null){
            fancommentuuid = UUID.randomUUID();
        }
        super.createdat=LocalDateTime.now();
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="fanboarduuid", nullable = false)
    private Fanboard fanboard;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="useruuid", nullable = false)
    private User user;

    private LocalDateTime createdat;
    private String content;
    private long ref;
    private long level;
    private long step;
}
