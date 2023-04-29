package com.capstone.furever.controllers;

import com.capstone.furever.models.*;
import com.capstone.furever.repositories.*;

import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private final UserRepository userDao;
    private final PostRepository postDao;
    private final PetRepository petsDao;
    private final EventRepository eventDao;
    private final CommentRepository commentDao;

    public UserController(UserRepository userDao, PostRepository postDao, PetRepository petsDao, EventRepository eventDao, CommentRepository commentDao) {
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.postDao = postDao;
        this.petsDao = petsDao;
        this.commentDao = commentDao;
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(currentUser.getId());

        List<User> followedUsers = userData.getFollowedUsers();
        model.addAttribute("followedUsers", followedUsers);
        List<Comments> comments = commentDao.findAll();
        List<User> users = userDao.findAll();
        List<Pet> pets = petsDao.findAll();
        List<Post> posts = postDao.findAll();
        List<Event> events = eventDao.findAll(); // or however you fetch the events

        List<Event> participatingEvents = new ArrayList<>();

        for(int i =0; i< events.size(); i++){
            if(events.get(i).hasParticipated(userData)){
                participatingEvents.add(events.get(i));
            }
        }
        model.addAttribute("events", participatingEvents);

        model.addAttribute("currentUser", userData);
        model.addAttribute("comments", comments);
        model.addAttribute("user", userData);
        model.addAttribute("users", users);
        model.addAttribute("pets", pets);
        model.addAttribute("posts", posts);
        return "users/profile";
    }

    @GetMapping(value = "/getUser")
    public ResponseEntity<User> getUser(@RequestParam(required = true) Long userId) {
        return new ResponseEntity<>(userDao.findById(userId).get(), HttpStatus.OK);
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "users/login";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Check if username is already taken
        try{
            boolean usernameTaken = userDao.existsByUsername(user.getUsername());
            System.out.println(usernameTaken);
            if (usernameTaken || user.getUsername().isEmpty()) {
                // Add message to model
                model.addAttribute("usernameTaken", "Username already taken or empty :( Sign-up again!");
                return "users/login";
            }else{
                String hashedPw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                user.setPassword(hashedPw);
                userDao.save(user);
                model.addAttribute("success", "Successfully Signed-Up! :)");
                return "users/login";
            }
        }catch (Exception e) {
            // Handle the exception
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred. Please try again later.");
            return "error";
        }
    }



    @GetMapping("/user/{id}/posts")
    public String userAds(@PathVariable long id, Model model) {
        Post post = postDao.findById(id).get();
        if (post.getId() == null) {
            return "posts/index";
        }
        User user = userDao.findById(id);
        List<Post> userPosts = user.getPosts();
        model.addAttribute("userAds", userPosts);
        return "posts/show";
    }


    @GetMapping("/profile/edit")
    public String showEditProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(user.getId()); // Getting data from the database first

        model.addAttribute("user", userData);
        return "users/edit-profile";
    }

    @PostMapping("/profile/edit")
    public String editUser(@ModelAttribute User user, Model model) {

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


    @PostMapping("/profile/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(currentUser.getId());
        try{
            if(userData.getId() == id){
                userDao.deleteById(id);
            }else{
                return "error/500";
            }
        }catch(Exception e){

        }
        return "users/login";
    }


    @GetMapping("/user/card")
    public String getPetIndexPage(Model model) {
        List<User> users = userDao.findAll();
        System.out.println(users.get(0).getUsername());
        model.addAttribute("users", users);
        return "users/user-card";
    }

    @GetMapping("/user/{id}")
    public String findPetById(@PathVariable long id, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData1 = userDao.findById(currentUser.getId());
        User currentUserData = userDao.findById(currentUser.getId());
        User userData = userDao.findById(id);


        List<Event> events = eventDao.findAll();
        List<Event> participatingEvents = new ArrayList<>();
        List<Comments> comments = commentDao.findAll();
        List<Comments> userComment = new ArrayList<>();
        List<Pet> petData = petsDao.findAll();
        List<User> followedUsers = userData.getFollowedUsers();
        List<Long> followedUsersId = new ArrayList<>();
        List<Post> posts = postDao.findAll();
        List<Post> userPost = new ArrayList<>();


        for (User followedUser : followedUsers) {
            System.out.println("[followedUsers]:" + followedUser.getUsername());
            System.out.println("[followedUsers ID]:" + followedUser.getId());
            followedUsersId.add(followedUser.getId());
        }


        // Filter events in which the user is participating

        for(int i =0; i< events.size(); i++){
            if(events.get(i).hasParticipated(userData)){
                participatingEvents.add(events.get(i));
            }
        }

        for(Post post: posts) {
            if(post.getUser().hasPosts(userData) ) {
                userPost.add(post);
            }
        }
        System.out.println("[userPost] = "+userPost);



        model.addAttribute("comments", comments);

        model.addAttribute("events", participatingEvents);
        model.addAttribute("posts", userPost);
        model.addAttribute("loggedInUser", currentUserData);
        System.out.println("userData1: " + currentUserData.getFirst_name());

        model.addAttribute("followedUsers", followedUsers);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("followedUsersId", followedUsersId);
        model.addAttribute("user", userData);
        model.addAttribute("pets", petData);


        return "users/user-show";
    }

    //Following users and friends below,
    // When button added, change it to post method
    @PostMapping("/users/{id}/follow")
    public String followUser(@PathVariable Long id, Model model, HttpServletRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUserData = userDao.findById(currentUser.getId());

        User user = userDao.findById(id).get();

        System.out.println("[[  --- Follow button clicked! --- ]]");
        if (!currentUserData.getFollowedUsers().contains(user) && !user.getFollowingUsers().contains(currentUserData)) {
            currentUserData.getFollowedUsers().add(user);
            user.getFollowingUsers().add(currentUserData);
        } else if (currentUserData.getFollowedUsers().contains(user)) {
            currentUserData.getFollowedUsers().remove(user);
            user.getFollowingUsers().remove(currentUserData);
        }
        userDao.save(currentUserData);

        model.addAttribute("currentUserData", currentUserData);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @GetMapping("/users/{id}/follow-search")
    public String followUser1(@PathVariable Long id, Model model, HttpServletRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUserData = userDao.findById(currentUser.getId());

        User user = userDao.findById(id).get();

        System.out.println("[[  --- Follow button clicked! --- ]]");
        if (!currentUserData.getFollowedUsers().contains(user) && !user.getFollowingUsers().contains(currentUserData)) {
            currentUserData.getFollowedUsers().add(user);
            user.getFollowingUsers().add(currentUserData);
        } else if (currentUserData.getFollowedUsers().contains(user)) {
            currentUserData.getFollowedUsers().remove(user);
            user.getFollowingUsers().remove(currentUserData);
        }
        userDao.save(currentUserData);

        model.addAttribute("currentUserData", currentUserData);
//        return "redirect:/friends" ;
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }



    @GetMapping("/following")
    public String followedUsers(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUserData = userDao.findById(currentUser.getId());
        List<User> followedUsers = currentUserData.getFollowedUsers();

        model.addAttribute("currentUserData", currentUserData);
        model.addAttribute("followedUsers", followedUsers);
        return "event/index";
    }

    @GetMapping("/user/{id}/show")
    @ResponseBody
    public User getUserById(@PathVariable Long id, Model model) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(loggedUser.getId());
        List<User> followedUsers = userData.getFollowedUsers();

        model.addAttribute("followedUsers", followedUsers);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if (currentPrincipalName != null && !currentPrincipalName.equalsIgnoreCase("anonymousUser")) {
            return userDao.findById(id).get();
        } else {
            return null;
        }
    }

    @GetMapping("/friends")
    public String showFriends(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(currentUser.getId());
        List<User> followedUsers = userData.getFollowedUsers();
        model.addAttribute("followedUsers", followedUsers);
        List<User> usersNotFollowing = new ArrayList<>();
        List<User> users = userDao.findAll();
        List<Pet> pets = petsDao.findAll();
        List<Post> posts = postDao.findAll();
        List<Event> events = eventDao.findAll(); // or however you fetch the events


        for (int i = 0; i < users.size(); i++) {
            System.out.println("[User]:" + users.get(i).getId());
            if (followedUsers.contains(users.get(i))) {
//                System.out.println("[[already following!!!]]");
            } else {
//                System.out.println("[[Not following!!!]]");
                usersNotFollowing.add(userDao.findById(users.get(i).getId()));
            }
        }

        model.addAttribute("usersNotFollowing", usersNotFollowing);
        model.addAttribute("events", events);
        model.addAttribute("user", userData);
        model.addAttribute("users", users);
        model.addAttribute("pets", pets);
        model.addAttribute("posts", posts);
        return "users/friend";
    }
}
