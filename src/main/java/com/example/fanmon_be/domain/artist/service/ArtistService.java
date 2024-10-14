package com.example.fanmon_be.domain.artist.service;

import com.example.fanmon_be.domain.artist.dao.ArtistDAO;
import com.example.fanmon_be.domain.artist.dto.ArtistResponse;
import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.management.dao.ManagementDAO;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.domain.user.dto.LoginRequest;
import com.example.fanmon_be.domain.user.dto.LoginResponse;
import com.example.fanmon_be.domain.user.enums.Role;
import com.example.fanmon_be.global.exception.ModelNotFoundException;
import com.example.fanmon_be.global.security.jwt.JwtPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ArtistService {
    @Autowired
    private ArtistDAO dao;
    @Autowired
    private ManagementDAO managementDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtPlugin jwtPlugin;
    @Autowired
    private ArtistDAO artistDAO;

    public List<Artist> findAll(){
        return dao.findAll();
    }

    public List<Artist> getArtistsByManagementuuid(UUID managementuuid){ return dao.findArtistsByManagementManagementuuid(managementuuid);}

    public Artist create(Artist artist){
        //이메일 중복 체크
        if(dao.existsByEmail(artist.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        artist.setRole(Role.ARTIST);
        artist.setPassword(passwordEncoder.encode(artist.getPassword()));
        return dao.save(artist);
    }

    public Artist getArtistById(UUID artistuuid){ return dao.findById(artistuuid).orElse(null);}

    public Artist updateArtist(UUID artistuuid, Artist artist){
        Management management = managementDAO.findByManagementuuid(artist.getManagement().getManagementuuid());
        artist.setManagement(management);
        artist.setArtistuuid(artistuuid); //수정하는거니까 그 아이디 그대로 넣어주기
        return dao.save(artist);
    }
    @Transactional
    public void deleteArtist(UUID artistuuid){
        artistDAO.deleteByArtistuuid(artistuuid);
    }

    public LoginResponse login(LoginRequest request) throws Exception {
        Artist artist = dao.findByEmail(request.getEmail())
                .orElseThrow(() -> new ModelNotFoundException(request.getEmail()));
        if(!passwordEncoder.matches(request.getPassword(), artist.getPassword())){
            throw new Exception("password 불일치");
        }
        String accessToken = jwtPlugin.generateAccessToken(
                artist.getArtistuuid().toString(),
                artist.getEmail(),
                artist.getRole().toString()
        );
        return new LoginResponse(
                accessToken,
                artist.getRole().toString()
        );
    }

    //management 별 아티스트 개수 COUNT
    public Long countByManagementuuid(UUID managementuuid){
        return artistDAO.countByManagementManagementuuid(managementuuid);
    }

    public ArtistResponse findById(UUID artistuuid) {
        Artist artist = dao.findById(artistuuid)
                .orElseThrow(() -> new ModelNotFoundException(artistuuid.toString()));
        return artist.toResponse();
    }
}
