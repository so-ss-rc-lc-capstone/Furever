package com.capstone.furever.controllers;

import com.capstone.furever.models.User;
import com.capstone.furever.repositories.PostRepository;
import com.capstone.furever.repositories.UserRepository;
import com.capstone.furever.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final EmailService emailService;

    private final PostRepository postDao;

    private final UserRepository userDao;



    public HomeController(PostRepository postDao, UserRepository userDao, EmailService emailService){
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

//Navigates to home page with a / url
    @GetMapping("/")
    public String returnHomePage(Model model){
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        return "home";
    }


    @GetMapping("/about")
    public String returnAboutPage(){
        return "about";
    }
}
