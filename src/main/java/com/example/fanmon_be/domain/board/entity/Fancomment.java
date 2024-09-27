package com.example.fanmon_be.domain.board.entity;

import com.example.fanmon_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name="fancomment")
public class Fancomment {
    @Id
    @Column(name = "fancommentuuid", nullable = false)
    private UUID fancommentuuid;

    @PrePersist
    public void generateUUID(){
        if(fancommentuuid == null){
            fancommentuuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="fanboarduuid", nullable = false)
    private Fanboard fanboard;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="useruuid", nullable = false)
    private User user;

    private LocalDateTime createdat;
    private String content;
    private long ref;
    private long level;
    private long step;
}
