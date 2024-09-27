package com.example.fanmon_be.domain.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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

//    @OneToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name="artistuuid", updatable = true, nullable = true)
//    private Artist artist;

    private Long price;
}
