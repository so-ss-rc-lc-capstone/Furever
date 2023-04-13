package com.codeup.codeupspringblog.controllers;


import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TestController {

    private final UserRepository userDao;

    public TestController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/alluser")
    public List<User> test(){
        List<User> users = userDao.findAll();
        return users;
    }
}
