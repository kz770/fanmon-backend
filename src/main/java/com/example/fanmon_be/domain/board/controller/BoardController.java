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
import com.example.fanmon_be.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/board/artistboard/{teamuuid}")
    public ResponseEntity<List<Artistboard>> getArtistBoardData(@PathVariable UUID teamuuid) {
        // board dto에 필요한 데이터 목록
        List<Artistboard> artistboards = artistBoardService.findById(teamuuid);
        System.out.println("artistboards = " + artistboards);
        return ResponseEntity.ok(artistboards);
    }
    @ResponseBody
    @GetMapping("/board/boardnotice/{teamuuid}")
    public ResponseEntity<List<Boardnotice>> getBoardNoticeData(@PathVariable UUID teamuuid) {
        // board notice에 필요한 데이터 목록
        List<Boardnotice> boardnotices = boardNoticeService.findById(teamuuid);
        System.out.println("boardnotices = " + boardnotices);
        return ResponseEntity.ok(boardnotices);
    }
    @ResponseBody
    @GetMapping("/board/fanboard/{teamuuid}")
    public ResponseEntity<List<Fanboard>> getFanBoardData(@PathVariable UUID teamuuid) {
        // fan board에 필요한 데이터 목록
        List<Fanboard> fanboards = fanBoardService.findById(teamuuid);
        System.out.println("fanboards = " + fanboards);
        return ResponseEntity.ok(fanboards);
    }

    @ResponseBody
    @GetMapping("/board/members/{teamuuid}")
    public ResponseEntity<List<Artist>> teamProfile(@PathVariable UUID teamuuid){
        List<Artist> artistList = new ArrayList<>();
//        artistTeamService.findByTeamTeamuuid();
        // 임시로 그냥 아티스트 넘겨주기
        artistList=artistService.findAll();
        return ResponseEntity.ok(artistList);
    }
    @PostMapping("/board/artistboard")
    public ResponseEntity<Artistboard> postOnArtistBoard(@RequestBody Artistboard newPosting){
        newPosting.setCreatedat(LocalDateTime.now());
//        artistBoardService.save(newPosting);
        newPosting.setArtistboarduuid(UUID.randomUUID());
        System.out.println("newPosting = " + newPosting);
        return ResponseEntity.ok(newPosting);
    }
    @PostMapping("/board/fanboard")
    public ResponseEntity<Fanboard> postOnFanboard(@RequestBody Fanboard newPosting){
        newPosting.setCreatedat(LocalDateTime.now());
        newPosting.setFanboarduuid(UUID.randomUUID());
        newPosting.setUser(userDAO.findById(newPosting.getUser().getUseruuid()).get());
        System.out.println("newPosting = " + newPosting);
        return ResponseEntity.ok(newPosting);
    }

}
