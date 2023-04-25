package com.capstone.furever.controllers;

import com.capstone.furever.repositories.PostRepository;
import com.capstone.furever.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    private final PostRepository postDao;
    private final UserRepository userDao;

    public AuthenticationController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Boolean isValidUser = authentication.isAuthenticated();
        System.out.println(isValidUser);
        return "users/login";
    }
}

