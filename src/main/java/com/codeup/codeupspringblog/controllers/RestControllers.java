package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
public class RestControllers {

    private final UserRepository userDao;

    public RestControllers(UserRepository userDao) {
        this.userDao = userDao;
    }


    @GetMapping("/users")
    public String getAllUsers() throws JsonProcessingException {
        List<User> users = userDao.findAll();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(users);
    }

    @GetMapping(path = "/users1", produces = "application/json")
    public List<User> getAllUsers1() {
        return userDao.findAll();
    }

}



//package com.codeup.codeupspringblog.controllers;
//
//import com.codeup.codeupspringblog.models.User;
//import com.codeup.codeupspringblog.repositories.UserRepository;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class RestControllers {
//
//
//    private final UserRepository userDao;
//
//    public RestControllers(UserRepository userDao) {
//        this.userDao = userDao;
//    }
//
//    @GetMapping("/api/users")
//    public List<User> getAllUsers() {
//        return userDao.findAll();
//    }
//}
