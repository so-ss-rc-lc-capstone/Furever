package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userDao;
    private final PostRepository postDao;


    public UserController(UserRepository userDao, PostRepository postDao){
        this.userDao = userDao;
        this.postDao = postDao;
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        String hashedPw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPw);
        userDao.save(user);
        return "redirect:/posts";
    }

//    @PostMapping("/register")
//    public String registerUser(@RequestParam(name="username") String username, @RequestParam(name="email") String email, @RequestParam(name="password") String password){
//        String hashedPw = BCrypt.hashpw(password, BCrypt.gensalt());
//        User user = new User(username, email, hashedPw);
//
//        userDao.save(user);
//        return "redirect:/posts/index";
//    }

    @GetMapping("/user/{id}/posts")
    public String userAds(@PathVariable long id, Model model){
        Post post = postDao.findById(id).get();
        if (post.getId()==null) {
            return "posts/index";
        }

        User user = userDao.findById(id);

        List<Post> userPosts = user.getPosts();
        model.addAttribute("userAds",userPosts);
        return "posts/show";
    }
}
