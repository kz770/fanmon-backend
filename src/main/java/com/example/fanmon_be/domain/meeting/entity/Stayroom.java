package com.example.fanmon_be.domain.meeting.entity;

import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.domain.meeting.enums.MeetingroomStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stayroom")
@Data
public class Stayroom {
    @Id
    private UUID stayuuid;

    @PrePersist
    public void generateUUID() {
        if (stayuuid == null) {
            stayuuid = UUID.randomUUID();
        }
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdat;

    private LocalDateTime meetingstartedat;

    @Enumerated(EnumType.STRING)
    private MeetingroomStatus status = MeetingroomStatus.STAY;

    @ManyToOne
    @JoinColumn(name = "teamuuid")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "managementuuid")
    private Management management;

    private LocalDateTime meetingendedat;

    /*
    @Lob는 JPA(Java Persistence API)에서 사용되는 어노테이션으로, Large Object 데이터를 나타내는 데 사용됩니다.

    Large Object 데이터는 긴 문자열, 이진 데이터 또는 바이너리 대량 데이터를 의미합니다. @Lob 어노테이션을 사용하면 이러한 대량의 데이터를 엔터티 클래스의 필드로 매핑할 수 있습니다.
    */
    @Lob
    @Column(name = "roomImage", columnDefinition="mediumblob")
    private byte[] roomImage;

    private int peopleNum;

    private int meetingTime;
    private int restTime;

    private boolean isPublic;
}
