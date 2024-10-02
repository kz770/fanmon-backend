package com.example.fanmon_be.domain.board.dto;

import com.example.fanmon_be.domain.board.entity.Artistboard;
import com.example.fanmon_be.domain.board.entity.Boardnotice;
import com.example.fanmon_be.domain.board.entity.Fanboard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private List<Artistboard> artistboards;
    private List<Boardnotice> boardnotices;
    private List<Fanboard> fanboards;
}
