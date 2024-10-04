package com.example.fanmon_be.domain.chat.dao;

import com.example.fanmon_be.domain.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageDAO extends JpaRepository<Message, UUID> {

}
