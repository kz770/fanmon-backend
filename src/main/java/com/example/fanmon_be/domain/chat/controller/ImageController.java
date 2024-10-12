package com.example.fanmon_be.domain.chat.controller;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@RestController
public class ImageController {
    @Autowired
    private ServletContext servletContext;

    //이미지 전송 메서드
    @PostMapping("/chat/sendImage")
    public ResponseEntity<String> sendImage(@RequestParam("image") MultipartFile file) {
        String savePath = servletContext.getRealPath("/resources/chat/"+file.getOriginalFilename());
        try {
            file.transferTo(new File(savePath)); // 파일 저장
            String returnPath = "http://localhost:8080/resources/chat/" + file.getOriginalFilename();
            System.out.println("path = " + returnPath);
            return ResponseEntity.ok(returnPath); // 이미지 url을 리턴한다
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }
}
