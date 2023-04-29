package com.capstone.furever.controllers;


import com.capstone.furever.models.Event;
import com.capstone.furever.models.User;
import com.capstone.furever.repositories.EventRepository;
import com.capstone.furever.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TestController {

    private final UserRepository userDao;
    private final EventRepository eventDao;

    public TestController(UserRepository userDao, EventRepository eventDao) {
        this.userDao = userDao;
        this.eventDao = eventDao;
    }

    @GetMapping("/alluser")
    public List<User> test(){
        List<User> users = userDao.findAll();
        return users;
    }

    @GetMapping("/allevents")
    public List<Event> events() { // Enclose the method body in curly braces
        List<Event> events = eventDao.findAll();
        return events;
    }
}
