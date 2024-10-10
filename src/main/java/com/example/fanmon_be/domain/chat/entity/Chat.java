package com.example.fanmon_be.domain.chat.entity;

import com.example.fanmon_be.domain.artist.entity.Artist;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name="chat")
public class Chat {
    @Id
    private UUID chatuuid;

    @PrePersist
    protected void onCreate() {
        if (chatuuid==null){
            this.chatuuid = UUID.randomUUID();
        }

    }

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="artistuuid", updatable = true, nullable = true)
    private Artist artist;

    private Long price;
}
