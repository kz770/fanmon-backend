package com.example.fanmon_be.domain.artist.dto;


import com.example.fanmon_be.domain.management.entity.Management;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistResponse {
    private UUID artistuuid;
    private String name;
    private Management management;
    private LocalDate debut;
    private String email;
    private LocalDate birth;
    private String fname;
    private String role;
}
