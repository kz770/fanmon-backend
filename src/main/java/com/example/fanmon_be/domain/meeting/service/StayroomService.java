package com.example.fanmon_be.domain.meeting.service;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.artist.service.ArtistService;
import com.example.fanmon_be.domain.meeting.dao.StayroomDAO;
import com.example.fanmon_be.domain.meeting.entity.Stayroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class StayroomService {
    @Autowired
    private StayroomDAO stayroomDAO;

    @Autowired
    private ArtistDAO teamDAO;

    public static final int peopleNum = 20;
    public static final int onceMeetingTime = 5;

    public Stayroom newStayroom(UUID artistuuid){
        Stayroom room = new Stayroom();
        LocalDateTime now = LocalDateTime.now();

        room.setCreatedat(now);
        room.setMeetingstartedat(now.plusHours(1));
        //room.setArtist(artistDAO.findById(artistuuid).orElse(null));
        room.setName(now+artistuuid.toString());
        stayroomDAO.save(room);

        return room;
    }
}
