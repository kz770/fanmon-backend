package com.example.fanmon_be.domain.board.controller;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.artist.service.TeamService;
import com.example.fanmon_be.domain.board.dto.BoardDTO;
import com.example.fanmon_be.domain.board.service.ArtistBoardService;
import com.example.fanmon_be.domain.board.service.BoardNoticeService;
import com.example.fanmon_be.domain.board.service.FanBoardService;
import com.example.fanmon_be.domain.board.service.FanCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @GetMapping("/board/{artistuuid}")
    public ResponseEntity<BoardDTO> getBoardData(@PathVariable UUID artistuuid){
        // board dto에 필요한 데이터 목록
        BoardDTO boardDTO = new BoardDTO(
                artistBoardService.findAll(),
                boardNoticeService.findAll(),
                fanBoardService.findAll());

        return ResponseEntity.ok(boardDTO);
    }
}
