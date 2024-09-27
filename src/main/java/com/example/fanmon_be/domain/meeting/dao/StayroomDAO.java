package com.example.fanmon_be.domain.meeting.dao;

import com.example.fanmon_be.domain.meeting.entity.Stayroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StayroomDAO extends JpaRepository<Stayroom, UUID> {
}
