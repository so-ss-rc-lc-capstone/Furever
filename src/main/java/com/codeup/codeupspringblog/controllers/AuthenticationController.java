package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuthenticationController {

    private final PostRepository postDao;

    public AuthenticationController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(user.getId());
//
//        if(user.getId() != 0){
//            List<Post> posts = postDao.findAll();
//            model.addAttribute("posts",posts);
//            return "posts/index";
//        }else{
//            return "users/login";
//        }
        return "users/login";

    }
}

