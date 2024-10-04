package com.example.fanmon_be.domain.artist.entity;

import com.example.fanmon_be.domain.management.entity.Management;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "team")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @Column(name = "teamuuid", nullable = false)
    private UUID teamuuid;

    @PrePersist
    public void generateUUID() {
        if (teamuuid == null) {
            teamuuid = UUID.randomUUID();
        }
    }

    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="managementuuid", insertable=true, updatable=true)
    private Management management;

    private LocalDate debut;
    private String fname;
    private String description;
    private int followers;

    @Transient
    private MultipartFile uploadfile;

    public Team(UUID teamuuid) {
        this.teamuuid = teamuuid;
    }
}
