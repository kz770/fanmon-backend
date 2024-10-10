package com.example.fanmon_be.domain.meeting.service;

import com.example.fanmon_be.domain.meeting.dao.StayuserlistDAO;
import com.example.fanmon_be.domain.meeting.entity.Stayroom;
import com.example.fanmon_be.domain.meeting.entity.Stayuserlist;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@Service
public class StayuserlistService {
    @Autowired
    private StayuserlistDAO stayuserlistDAO;

    @Autowired
    private UserDAO userDAO;

    public List<User> findRandomUserList(int peoplenum, String teamname){
        List<User> list = null;
        User randomUser = stayuserlistDAO.findRandomUser(teamname);
        int maxnum = stayuserlistDAO.randomUserMaxNum(teamname);

        while(list.size() < peoplenum || list.size() < maxnum){
            if(!list.contains(randomUser)){
                list.add(randomUser);
            }
        }

        return list;
    }

    public void matchUserList(Stayroom room){
        Stayuserlist li = new Stayuserlist();
        li.setStayroom(room);
    }
}
