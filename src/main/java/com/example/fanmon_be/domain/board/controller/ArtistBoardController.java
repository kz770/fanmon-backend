package com.example.fanmon_be.domain.board.controller;

import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.board.dao.ArtistboardDAO;
import com.example.fanmon_be.domain.board.entity.Artistboard;
import com.example.fanmon_be.domain.board.entity.Fanboard;
import com.example.fanmon_be.domain.board.service.ArtistBoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/board/artistboard")
public class ArtistBoardController {
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ArtistBoardService artistBoardService;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/{teamuuid}")
    public ResponseEntity<List<Artistboard>> getList(@PathVariable UUID teamuuid){
        List<Artistboard> artistboardList=artistBoardService.findById(teamuuid);
        return ResponseEntity.ok(artistboardList);
    }

    @PostMapping("")
    public ResponseEntity<Artistboard> postOnArtistBoard(@RequestParam("post") String post,
                                  @RequestParam(value = "image", required = false) MultipartFile image){
        try {
            Artistboard artistboard = objectMapper.readValue(post, Artistboard.class);
            if (image !=null && !image.isEmpty()){
                String savePath = servletContext.getRealPath("/resources/board/" + image.getOriginalFilename());
                image.transferTo(new File(savePath)); // 파일 저장
                String fname = "http://localhost:8080/resources/board/" + image.getOriginalFilename();
                artistboard.setFname(fname);
                System.out.println("path = " + fname);
            }
            artistBoardService.save(artistboard);
            return ResponseEntity.ok(artistboard); // 이미지 url을 리턴한다
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 게시물 수정
    @PutMapping("/put")
    public ResponseEntity<Artistboard> update(
            @RequestParam("post") String post,
            @RequestParam(value = "image", required = false) MultipartFile image) { // 이미지가 optional

        try {
            Artistboard artistboard = objectMapper.readValue(post, Artistboard.class);
            if (image != null && !image.isEmpty()) { // 이미지가 있는 경우
                String savePath = servletContext.getRealPath("/resources/board/" + image.getOriginalFilename());
                image.transferTo(new File(savePath)); // 파일 저장
                String fname = "http://localhost:8080/resources/board/" + image.getOriginalFilename();
                artistboard.setFname(fname);
                System.out.println("path = " + fname);
            } else {
                // 이미지가 없는 경우
                String prevFname=artistboard.getFname();
                if (prevFname!=null && !prevFname.equals("")){
                    artistboard.setFname(prevFname); // 기존 이미지 URL로 설정
                }
            }
            artistBoardService.save(artistboard); // 게시물 저장
            return ResponseEntity.ok(artistboard); // 저장된 게시물 반환
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/{artistboarduuid}")
    public void deletePost(@PathVariable UUID artistboarduuid){
        artistBoardService.delete(artistboarduuid);
    }



}
