package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import com.codeup.codeupspringblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/")
    @ResponseBody
    public String welcome(){
//        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long loggedID = loggedUser.getId();
//
//        User user = userDao.findById(loggedID).get();
//
//
//        Post post = postDao.findById().get()
//        emailService.prepareAndSend(post);
//        System.out.println("Email sent!");
        return "This is the landing page!";
    }
}
