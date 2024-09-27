package com.example.fanmon_be.domain.meeting.service;

import com.example.fanmon_be.domain.meeting.dao.StayroomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StayroomService {
    @Autowired
    private StayroomDAO stayroomDAO;
}
