package com.example.fanmon_be.domain.artist.entity;

import com.example.fanmon_be.domain.artist.dto.ArtistResponse;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.domain.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "artist")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @Id
    @Column(name = "artistuuid", nullable = false)
    private UUID artistuuid;

    @PrePersist
    public void generateUUID() {
        if (artistuuid == null) {
            artistuuid = UUID.randomUUID();
        }
    }

    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="managementuuid", insertable=true, updatable=true)
    private Management management;

    private LocalDate debut;
    private String email;
    private String password;
    private LocalDate birth;
    private String fname;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.ARTIST;


    @Transient
    @JsonIgnore
    private MultipartFile uploadfile;

    public ArtistResponse toResponse(){
        return new ArtistResponse(
                this.artistuuid,
                this.name,
                this.management,
                this.debut,
                this.email,
                this.birth,
                this.fname,
                this.role.toString()
        );
    }
}
