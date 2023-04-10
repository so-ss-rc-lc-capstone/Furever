package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Pet;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PetRepository;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private final UserRepository userDao;
    private final PostRepository postDao;

    private final PetRepository petsDao;



    public UserController(UserRepository userDao, PostRepository postDao, PetRepository petsDao){
        this.userDao = userDao;
        this.postDao = postDao;
        this.petsDao = petsDao;
    }


    @GetMapping("/profile")
    public String showProfile(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Pet> pets = petsDao.findAll();
        List<Post> posts = postDao.findAll();

        model.addAttribute("user", user);
        model.addAttribute("pets",pets);
        model.addAttribute("posts", posts);
        return "users/profile";
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
        return "users/login";
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



    @GetMapping("/profile/edit")
    public String showEditProfile( Model model){
//        Post post = postDao.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(user.getId()); // Getting data from the database first

//        System.out.println(user.getId());
//        System.out.println(post.getUser().getId());
        model.addAttribute("user", userData);
        return "users/edit-profile";
    }




    @PostMapping("/profile/edit")
    public String editUser(@ModelAttribute User user, Model model){

        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(loggedUser.getId());

        userData.setFirst_name(user.getFirst_name());
        userData.setLast_name(user.getLast_name());
        userData.setPhone_number(user.getPhone_number());
        userData.setBio(user.getBio());
        userData.setAddress(user.getAddress());
        userData.setZip_code(user.getZip_code());
        userData.setGender(user.getGender());
        userDao.save(userData);



//        System.out.println(user.getId());
//        System.out.println(post.getUser().getId());
        model.addAttribute("user", userData);
        return "users/profile";

    }

    @GetMapping("/api/users")
    public String getUsers(@RequestParam String query) throws JsonProcessingException {
        List<User> users = userDao.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(users);
        return json;
    }
}
