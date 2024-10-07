package com.example.fanmon_be.domain.chat.entity;

import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.chat.enums.MessageFrom;
import com.example.fanmon_be.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="usermessage")
@Data
public class UserMessage implements Serializable {
    @Id
    private UUID usermessageuuid;
    @PrePersist
    protected void onCreate() {
        if (usermessageuuid==null){
            this.usermessageuuid = UUID.randomUUID();
        }
    }

    private String messagetext;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name="artistuuid")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name="useruuid", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name="chatuuid")
    private Chat chat;
}
