package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Event;
import com.codeup.codeupspringblog.models.Pet;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.EventRepository;
import com.codeup.codeupspringblog.repositories.PetRepository;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarOutputStream;

@Controller
public class UserController {
    @Autowired
    private final UserRepository userDao;
    private final PostRepository postDao;

    private final PetRepository petsDao;

    private final EventRepository eventDao;



    public UserController(UserRepository userDao, PostRepository postDao, PetRepository petsDao, EventRepository eventDao){
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.postDao = postDao;
        this.petsDao = petsDao;
    }


    @GetMapping("/profile")
    public String showProfile(Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(currentUser.getId());

        List<User> followedUsers = userData.getFollowedUsers();
        model.addAttribute("followedUsers",followedUsers);

        List<User> users = userDao.findAll();
        List<Pet> pets = petsDao.findAll();
        List<Post> posts = postDao.findAll();
        List<Event> events = eventDao.findAll(); // or however you fetch the events

        model.addAttribute("events", events);
        model.addAttribute("user", userData);
        model.addAttribute("users", users);
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
        userData.setGender(user.getGender());
        userData.setProfilePhoto(user.getProfilePhoto());
        userDao.save(userData);
        model.addAttribute("user", userData);
        return "redirect:/profile";

    }


    @GetMapping("/user/card")
    public String getPetIndexPage(Model model){

        List<User> users = userDao.findAll();
        System.out.println(users.get(0).getUsername());
        model.addAttribute("users", users);
//        List<Post> filteredPostsList = posts
//                .stream()
//                .filter(product -> product.getPriceInCents()<1000)
//                .collect(Collectors.toList());
//        model.addAttribute("posts", filteredPostsList);

        return "users/user-card";
    }

    @GetMapping("/user/{id}")
    public String findPetById(@PathVariable long id , Model model) {
        List<Pet> petData = petsDao.findAll();
        User userData = userDao.findById(id);
        model.addAttribute("user",userData);
        model.addAttribute("pets", petData);
        return "users/user-show";
    }


    //Following users and friends below,
    // When button added, change it to post method
    @PostMapping("/users/{id}/follow")
//    @ResponseBody
    public String followUser(@PathVariable Long id, Model model, @RequestParam){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUserData = userDao.findById(currentUser.getId());
        model.addAttribute("currentUserData", currentUserData);

        User user = userDao.findById(id).get();

        if(!currentUserData.getFollowedUsers().contains(user) && !user.getFollowingUsers().contains(currentUserData)){
            currentUserData.getFollowedUsers().add(user);
            user.getFollowingUsers().add(currentUserData);
        }
        else if (currentUserData.getFollowedUsers().contains(user)) {
            currentUserData.getFollowedUsers().remove(user);
            user.getFollowingUsers().remove(currentUserData);
        }
       userDao.save(currentUserData);

//        return userDao.findById(id).get();
        return "redirect:/events";
    }


    @GetMapping("/following")
    public String followedUsers(Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User currentUserData = userDao.findById(currentUser.getId());

        List<User> followedUsers = currentUserData.getFollowedUsers();

        System.out.println(followedUsers);
        model.addAttribute("currentUserData", currentUserData);
        model.addAttribute("followedUsers", followedUsers);
        return "event/index";
    }






//    @GetMapping("/users.json")
//    public @ResponseBody List<User> viewAllAdsInJSONFormat() {
//        return userDao.findAll();
//    }
//
//    @GetMapping("/users/ajax")
//    public String viewAllAdsWithAjax() {
//        return "users/friend";
//    }



    @GetMapping("/user/{id}/show")
    @ResponseBody
    public User getUserById(@PathVariable Long id, Model model) {



        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(loggedUser.getId());
        List<User> followedUsers = userData.getFollowedUsers();

        model.addAttribute("followedUsers",followedUsers);



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();



        if(currentPrincipalName!=null && !currentPrincipalName.equalsIgnoreCase( "anonymousUser")  ){
            return userDao.findById(id).get();
        }else{
            return null;
        }
    }

    // try & catch


    @GetMapping("/friends")
    public String showFriends(Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(currentUser.getId());

        List<User> followedUsers = userData.getFollowedUsers();
        model.addAttribute("followedUsers",followedUsers);

        List<User> users = userDao.findAll();
        List<Pet> pets = petsDao.findAll();
        List<Post> posts = postDao.findAll();
        List<Event> events = eventDao.findAll(); // or however you fetch the events

        model.addAttribute("events", events);
        model.addAttribute("user", userData);
        model.addAttribute("users", users);
        model.addAttribute("pets",pets);
        model.addAttribute("posts", posts);
        return "users/friend";
    }

}
