package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.chat.entity.ArtistMessage;
import com.example.fanmon_be.domain.chat.entity.UserMessage;
import com.example.fanmon_be.domain.chat.service.ChatService;
import com.example.fanmon_be.domain.chat.service.MessageService;
import com.example.fanmon_be.domain.chat.service.SubscribeService;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.enums.UserStatus;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class MessageController {

    @Autowired
    ChatService chatService;
    @Autowired
    SubscribeService subscribeService;
    @Autowired
    UserDAO userDAO;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ArtistDAO artistDAO;

    // 아티스트 -> 유저 메세지 전송 메서드
    @MessageMapping("/{artistuuid}/toFans")    // uuid로 메세지를 발행한 아티스트 구분
    @SendTo("/sub/{artistuuid}/toFans")    // 특정 artist를 구독한 유저 모두에게 메세지 전송
    public ArtistMessage sendFromArtist(@Payload ArtistMessage message, @DestinationVariable UUID artistuuid) {
        // artistuuid로 chatuuid 불러오기
        UUID chatuuid=chatService.findByArtistuuid(artistuuid);
        System.out.println("chatuuid = " + chatuuid);
        // 전송시간과 UUID 설정
        message.setTimestamp(LocalDateTime.now());
        message.setArtistmessageuuid(UUID.randomUUID());
        System.out.println("message : "+message);
        redisTemplate.opsForList().rightPush("ARTIST:"+ chatuuid, message);
        return message;
    }
    //유저 -> 아티스트 메세지 전송 메서드
    @MessageMapping("/{artistuuid}/{useruuid}/toArtist")     // 어떤 아티스트에게 어떤 유저가 메세지를 발행했는지 구분
    @SendTo("/sub/{artistuuid}/fromFans")     // 특정 아티스트에게 모든 유저의 메세지 전송
    public UserMessage sendFromFan(@Payload UserMessage message, @DestinationVariable UUID artistuuid, @DestinationVariable UUID useruuid) {
        // artistuuid로 chatuuid 불러오기
        UUID chatuuid=chatService.findByArtistuuid(artistuuid);
        System.out.println("chatuuid = " + chatuuid);
        // 전송시간과 UUID 설정
        message.setTimestamp(LocalDateTime.now());
        message.setUsermessageuuid(UUID.randomUUID());
        // 레디스에 메세지 임시저장
        redisTemplate.opsForList().rightPush("USER:" + chatuuid, message);
        System.out.println("message : "+message);
        return message;
    }

    // 임시 세션 저장 메서드
    @PostMapping("/chat/login")
    @ResponseBody
    public ResponseEntity<?> login(HttpSession session, @RequestBody LoginRequest req){
        LoginResponse res=new LoginResponse();
        //String으로 전달받은 UUID를 변환
        UUID requuid=UUID.fromString(req.getUuid());
        System.out.println("req = " + req);
        // 사용자 조회
        Optional<User> user = userDAO.findById(requuid);
        if (user.isPresent()) {
            res.setUser("USER");
            res.setUuid(user.get().getUseruuid());
            res.setStat(user.get().getStatus());
            return ResponseEntity.ok(res);
        }
        // 아티스트 조회
        Optional<Artist> artist = artistDAO.findById(requuid);
        if (artist.isPresent()) {
            res.setUser("ARTIST");
            res.setUuid(artist.get().getArtistuuid());
            return ResponseEntity.ok(res);
        }
        // 사용자와 아티스트 모두 없을 때
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User or Artist not found");
    }
    @GetMapping("/chat/logout")
    @ResponseBody
    public String logout(HttpSession session){

        session.removeAttribute("user");
        return "logout success";
    }

}

// 로그인 요청을 위한 DTO
@Data
class LoginRequest {
    private String uuid;
}

// 로그인 응답을 위한 DTO
@Data
class LoginResponse {
    private String user;
    private UUID uuid;
    private UserStatus stat;
}
