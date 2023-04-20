package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.*;
import com.codeup.codeupspringblog.repositories.CommentRepository;
import com.codeup.codeupspringblog.repositories.EventRepository;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import com.codeup.codeupspringblog.services.EmailService;
import org.attoparser.dom.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {


    // Dependency Injection
    private final PostRepository postDao;
    private final UserRepository usersDao;

    private final CommentRepository commentDao;

    private final EmailService emailService;

    private final PostService postService;


    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService, PostService postService, CommentRepository commentDao) {
        this.postDao = postDao;
        this.usersDao = userDao;
        this.emailService = emailService;
        this.postService = postService;
        this.commentDao = commentDao;
    }

    @GetMapping("/posts")
    public String allEvents(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = usersDao.findById(currentUser.getId());

        List<Post> posts = postDao.findAll();
        List<User> followedUsers = userData.getFollowedUsers();
        List<User> usersNotFollowing = new ArrayList<>();
        List<User> users = usersDao.findAll();
        List<Comments> comments = commentDao.findAll();

        for(int i=0; i<users.size(); i++){
            System.out.println("[User]:"+ users.get(i).getId());
            if(followedUsers.contains(users.get(i))){
                System.out.println("[[already following!!!]]");
            }else{
                System.out.println("[[Not following!!!]]");
                usersNotFollowing.add(usersDao.findById(users.get(i).getId()));
            }
        }
        model.addAttribute("comments", comments);
        model.addAttribute("followedUsers",followedUsers);
        model.addAttribute("posts", posts);
        model.addAttribute("usersNotFollowing", usersNotFollowing);
        model.addAttribute("user", userData);
        model.addAttribute("comments", new Comments());
        model.addAttribute("users",users);
        return "posts/index";
    }


//    @GetMapping("/events/{id}/find")
//    public String findEvent(@PathVariable long id, Model model) {
//        Event event = eventsDao.findById(id).get();
//        List<EventParticipation> participations = event.getParticipations();
//        List<User> participants = new ArrayList<>();
//        for (EventParticipation participation : participations) {
//            participants.add(participation.getUser());
//        }
//        model.addAttribute("participants", participants);
//        model.addAttribute("event", event);
//        return "event/show";
//    }


    @GetMapping("/posts/{id}/show")
    public String showComment(@PathVariable Long id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = usersDao.findById(user.getId());
        Post post = postDao.findById(id).get();

        List<Comments> comments = post.getComments();
        List<User> users = usersDao.findAll();

        model.addAttribute("comments", new Comments());
        model.addAttribute("user", userData);
        model.addAttribute("post", post);
        model.addAttribute("comment", comments);
        return "posts/comment-show";
    }


@PostMapping("/comment/{id}/create")
public String createComment(@ModelAttribute Comments comments, @PathVariable Long id) {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User userData = usersDao.findById(currentUser.getId());
    Post post = postDao.findById(id).get();

    post.getComments().add(comments);

    System.out.println("THE POST" + post);
    System.out.println("Current id of logged in:" + userData);
    comments.setUser(userData);
    comments.setPost(post);
    comments.setCreated_at(LocalDateTime.now());

    Comments comment = new Comments(comments);
    commentDao.save(comment);
    return "redirect:/posts";
}




//Form Model Binding
    @GetMapping("/posts/create")
    public String showCreate(Model model){
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post){

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userData = usersDao.findById(currentUser.getId());
        post.setUser(userData);
        post.setCreated_at(LocalDateTime.now());
        postDao.save(post);
        emailService.prepareAndSend(post);
        return "redirect:/posts"; // go to controller
//        return "<p>Post: "+name+"</p><p>Price: " +price+"</p>";
    }



    @GetMapping("/posts/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model){
//        Post post = postDao.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postDao.findById(id).get(); // Getting data from the database first

//        System.out.println(user.getId());
//        System.out.println(post.getUser().getId());
        if(user.getId() == post.getUser().getId()){
            model.addAttribute("post", postDao.findById(id).get());
            return "posts/edit";
        }else {
            return "redirect:/posts";
        }
    }


    @PostMapping("/posts/{id}/edit")
    public String editPost( @ModelAttribute Post post, @PathVariable Long id , Model model){
        Post postData = postDao.findById(id).get(); // Getting data from the database first

        postData.setBody(post.getBody());
        postDao.save(postData);
        model.addAttribute("posts", postData);
        return "redirect:/posts/{id}"; // go to controller
    }


//    @GetMapping("/posts/edit")
//    public String editPost(){
//        User user = userDao.findById(2);
//        Post post = new Post(1L,"Dog","woof, woof, wooooof!!!",user);
//        postDao.save(post);
//        return "redirect:/posts/index"; // go to controller
////        return "<p>Post: "+name+"</p><p>Price: " +price+"</p>";
//    }



    @GetMapping("/posts/card")
    public String getPostCard(Model model){

        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
//        List<Post> filteredPostsList = posts
//                .stream()
//                .filter(product -> product.getPriceInCents()<1000)
//                .collect(Collectors.toList());
//        model.addAttribute("posts", filteredPostsList);

        return "posts/post-card";
    }



    @GetMapping("/posts/{n}/delete")
    public String deletePost(@PathVariable long n){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postDao.findById(n).get();
//        System.out.println(user.getId());
//        System.out.println(post.getUser().getId());
        if(user.getId() == post.getUser().getId()){
            postDao.deleteById(n);
        }
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}")
    public String findPostById(@PathVariable long id , Model model) {
        Post post = postDao.findById(id).get();
        if (post.getId()==null) {
            return "posts/index";
        }

        model.addAttribute("post", post);
        return "posts/show";
    }


    @PostMapping("/posts/{id}/like")
    public String likePost(@PathVariable Long id, Principal principal) {
        User user = usersDao.findByUsername(principal.getName());
        Post post = postDao.findById(id).get();
        System.out.println(post.hasLiked(user));
        if(post.hasLiked(user)){
            postService.decrementLikes(id, user);
        }else{
            postService.incrementLikes(id, user);
        }
        return "redirect:/posts";
    }





}



