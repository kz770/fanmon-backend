package com.example.fanmon_be.domain.chat.entity;

import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="message")
@Data
public class Message {
    @Id
    private UUID messageuuid;
    private String messagetext;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name="artistuuid")
    private Artist artist;
    @ManyToOne
    @JoinColumn(name="useruuid")
    private User user;
}
