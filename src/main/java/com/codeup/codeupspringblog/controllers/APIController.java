package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    private final UserRepository userDao;

    public APIController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam String query) throws JsonProcessingException {
        List<User> users = userDao.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(users);
        return users;
    }
}
