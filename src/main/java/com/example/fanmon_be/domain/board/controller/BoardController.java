package com.example.fanmon_be.domain.board.controller;

import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.artist.service.ArtistService;
import com.example.fanmon_be.domain.artist.service.ArtistTeamService;
import com.example.fanmon_be.domain.artist.service.TeamService;
import com.example.fanmon_be.domain.board.entity.Artistboard;
import com.example.fanmon_be.domain.board.entity.Boardnotice;
import com.example.fanmon_be.domain.board.entity.Fanboard;
import com.example.fanmon_be.domain.board.service.ArtistBoardService;
import com.example.fanmon_be.domain.board.service.BoardNoticeService;
import com.example.fanmon_be.domain.board.service.FanBoardService;
import com.example.fanmon_be.domain.board.service.FanCommentService;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class BoardController {
    @Autowired
    TeamService teamService;
    @Autowired
    ArtistBoardService artistBoardService;
    @Autowired
    BoardNoticeService boardNoticeService;
    @Autowired
    FanBoardService fanBoardService;
    @Autowired
    FanCommentService fanCommentService;
    @Autowired
    ArtistTeamService artistTeamService;
    @Autowired
    ArtistService artistService;
    @Autowired
    private UserDAO userDAO;

    @ResponseBody
    @GetMapping("/board/boardnotice/{teamuuid}")
    public ResponseEntity<List<Boardnotice>> getBoardNoticeData(@PathVariable UUID teamuuid) {
        // board notice에 필요한 데이터 목록
        List<Boardnotice> boardnotices = boardNoticeService.findById(teamuuid);
//        System.out.println("boardnotices = " + boardnotices);
        return ResponseEntity.ok(boardnotices);
    }
    @ResponseBody
    @GetMapping("/board/fanboard/{teamuuid}")
    public ResponseEntity<List<Fanboard>> getFanBoardData(@PathVariable UUID teamuuid) {
        // fan board에 필요한 데이터 목록
        List<Fanboard> fanboards = fanBoardService.fanBoardData(teamuuid);
        return ResponseEntity.ok(fanboards);
    }

    @PostMapping("/board/fanboard")
    public ResponseEntity<Fanboard> postOnFanboard(@RequestBody Fanboard newPosting){
        fanBoardService.save(newPosting);
        System.out.println("newPosting = " + newPosting);
        return ResponseEntity.ok(newPosting);
    }

    @DeleteMapping("/board/fanboard/{fanboarduuid}")
    public ResponseEntity<Void> deleteFanboard(@PathVariable UUID fanboarduuid){
        fanBoardService.delete(fanboarduuid);
        return ResponseEntity.noContent().build();
    }
}
