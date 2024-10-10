package com.example.fanmon_be.domain.chat.entity;

import com.example.fanmon_be.domain.chat.enums.SubscriptionStatus;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="subscribe")
public class Subscribe {
    @Id
    private UUID subscribeuuid;

    @PrePersist
    protected void generateBaseColumns() {
        if (subscribeuuid==null){
            this.subscribeuuid = UUID.randomUUID();
            this.startsubscription = LocalDateTime.now();
        }
        this.endsubscription = this.startsubscription.plusMonths(1);
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="useruuid")
    private User user;

    @ManyToOne(cascade=CascadeType.REMOVE)
    @JoinColumn(name="chatuuid")
    private Chat chat;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;
    private LocalDateTime startsubscription;
    private LocalDateTime endsubscription;
}
