package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.chat.dao.UserMessageDAO;
import com.example.fanmon_be.domain.chat.entity.ArtistMessage;
import com.example.fanmon_be.domain.chat.entity.UserMessage;
import com.example.fanmon_be.domain.chat.entity.Subscribe;
import com.example.fanmon_be.domain.chat.service.ChatService;
import com.example.fanmon_be.domain.chat.service.MessageService;
import com.example.fanmon_be.domain.chat.service.SubscribeService;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.enums.UserStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class ChatController {

    @Autowired
    ChatService chatService;
    @Autowired
    SubscribeService subscribeService;
    @Autowired
    UserDAO userDAO;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ArtistDAO artistDAO;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMessageDAO userMessageDAO;

    //이미지 전송 메서드
    @MessageMapping("/sendImage")
    @SendTo("/sub/{artistuuid}/toFans")
    public void handleImageMessage(@Payload ArtistMessage message) {
        // 메시지에서 Base64 인코딩된 이미지 데이터 추출
        int index = message.getMessagetext().indexOf("/");
        String fname = message.getMessagetext().substring(0,index).trim();
        System.out.println("fname = " + fname);
        String imageData=message.getMessagetext().substring(index+1).trim();
        FileOutputStream fos = null;
//        String path=req.getServletContext().getRealPath("/resources/images");
//        System.out.println("path = " + path);
//            try {
//                JsonNode jsonNode = new ObjectMapper().readTree(message.getMessagetext());
//                imageData = jsonNode.get("imageData").asText();
//                // 이미지 데이터 처리
//                String base64Image = imageData.split(",")[1]; // "data:image/png;base64," 부분 제거
//                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//
//                File file = new File("src/main/webapp/images/image.png"); // 원하는 경로와 파일 이름
//                fos = new FileOutputStream(file);
//                fos.write(imageBytes);
//            } catch (Exception e) {
//                e.printStackTrace(); // 예외 처리
//            } finally {
//                try {
//                    if (fos != null) {
//                        fos.close(); // 파일 스트림 닫기
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            // 필요 시 클라이언트에 다른 메시지 전송
            // Simulate sending back the same image data (for demonstration)
    }

    // 불량 사용자 차단
    @Transactional
    @PostMapping("/chat/block")
    public void block(UUID messageuuid){
        // 레디스 캐시 처리 후 가능 ㅠㅠ
        UserMessage userMessage = messageService.findById(messageuuid);
        System.out.println("userMessage = " + userMessage);
        User user=userMessage.getUser();
        // 유저 상태 변경
        user.setStatus(UserStatus.BANNED);
        userDAO.save(user);
    }

//     메세지 리스트를 반환
    @ResponseBody
    @GetMapping("/chat/messages/{chatuuid}")
    public List<Object> list(@PathVariable UUID chatuuid){
        // DB에 저장된 메세지 불러오기
        return messageService.getAllMessages(chatuuid);
    }

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

    // 구독 확인 메서드
    @ResponseBody
    @GetMapping("/chat/subscribe/{chatuuid}")
    public ResponseEntity<Subscribe> subscriptionCheck(@PathVariable UUID chatuuid, HttpSession session){
        User user=(User)session.getAttribute("user");
        // subscribe 테이블에서 사용자가 해당 artist를 구독중인치 체크.
        Subscribe subscription = subscribeService.getSubscription2(user.getUseruuid(), chatuuid);
        if (subscription!=null){
            // 구독중이라면 구독 데이터 반환
            return ResponseEntity.ok(subscription);
        }
        // 만약 아니라면 널 값을 반환
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
