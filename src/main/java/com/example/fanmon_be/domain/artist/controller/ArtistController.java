package com.example.fanmon_be.domain.artist.controller;


import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.artist.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @GetMapping("/test")
    @ResponseBody
    public List<Artist> test() {
        return artistService.findAll();
    }

}
