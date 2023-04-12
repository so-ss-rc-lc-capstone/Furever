package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

        System.out.println(currentPrincipalName);

        Boolean isValidUser = authentication.isAuthenticated();
        System.out.println(isValidUser);

//        if(!currentPrincipalName.equalsIgnoreCase( "anonymousUser") || currentPrincipalName !=null ){
//            return "redirect:/events";
////            return "users/login";
//
//        }



        return "users/login";
//        return "redirect:/events";

    }

//    @PostMapping("/login")
//    public String loadFromLoginForm(Model model) {
//
//        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        System.out.println(currentUser.getId());
//        if(currentUser.getId() != 0){
//            User userData = userDao.findById(currentUser.getId());
//            model.addAttribute("user", userData);
//            return "redirect:/profile";
//        }else{
//            return "users/login";
//        }
//    }
}

