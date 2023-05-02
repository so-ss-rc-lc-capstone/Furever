package com.capstone.furever.controllers;

import com.capstone.furever.models.*;
import com.capstone.furever.repositories.CommentRepository;
import com.capstone.furever.repositories.PostRepository;
import com.capstone.furever.repositories.UserRepository;
import com.capstone.furever.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

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
        System.out.println("hello");
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = usersDao.findById(currentUser.getId());

        List<Post> posts = postDao.findAll();
        Collections.reverse(posts);
        List<User> followedUsers = userData.getFollowedUsers();
        List<User> users = usersDao.findAll();
        List<Comments> comments = commentDao.findAll();
        Collections.reverse(comments);
        List<User> usersNotFollowing = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            System.out.println("[User]:" + users.get(i).getId());
            if (followedUsers.contains(users.get(i))) {
                System.out.println("[[already following!!!]]");
            } else {
                System.out.println("[[Not following!!!]]");
                usersNotFollowing.add(usersDao.findById(users.get(i).getId()));
            }
        }

        model.addAttribute("followedUsers", followedUsers);
        model.addAttribute("posts", posts);
        model.addAttribute("usersNotFollowing", usersNotFollowing);
        model.addAttribute("user", userData);
        model.addAttribute("comments", comments); // add the comments list to the model once
        model.addAttribute("users", users);
        return "posts/index";
    }


    @GetMapping("/posts/{id}/show")
    public String showComment(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = usersDao.findById(user.getId());
        Post post = postDao.findById(id).get();

        List<Comments> comments = post.getComments();
        Collections.reverse(comments);

        List<User> users = usersDao.findAll();
        model.addAttribute("comments", new Comments());
        model.addAttribute("user", userData);
        model.addAttribute("post", post);
        model.addAttribute("comment", comments);
        return "posts/comment-show";
    }

    @PostMapping("/comment/{id}/create")
    public String createComment(@ModelAttribute Comments comments, @PathVariable Long id, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUserData = usersDao.findById(currentUser.getId());
        model.addAttribute("user", currentUserData);

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

    @PostMapping("/comment/{id}/create-show")
    public String createCommentShow(@ModelAttribute Comments comments, @PathVariable Long id) {
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
        return "redirect:/posts/{id}/show";
    }

    @PostMapping("/comment/{n}/delete")
    public String deleteComment(@PathVariable long n, HttpServletRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUserDb = usersDao.findById(currentUser.getId());

        List<Comments> comments = commentDao.findAll();
        Comments commentDB = commentDao.findById(n).get();

        if(currentUserDb.getId() == commentDB.getUser().getId()){
            commentDao.deleteById(n);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }



    //Form Model Binding
    @GetMapping("/posts/create")
    public String showCreate(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = usersDao.findById(currentUser.getId());
        post.setUser(userData);
        post.setCreated_at(LocalDateTime.now());
        postDao.save(post);
//        emailService.prepareAndSend(post);
        return "redirect:/posts"; // go to controller

    }

    @GetMapping("/posts/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUserData = usersDao.findById(currentUser.getId());
        model.addAttribute("user", currentUserData);        Post post = postDao.findById(id).get(); // Getting data from the database first
        if (currentUser.getId() == post.getUser().getId()) {
            model.addAttribute("post", postDao.findById(id).get());
            return "posts/edit";
        } else {
            return "redirect:/posts";
        }
    }

    @PostMapping("/posts/{id}/edit")
    public String editPost(@ModelAttribute Post post, @PathVariable Long id, Model model) {
        Post postData = postDao.findById(id).get(); // Getting data from the database first

        postData.setBody(post.getBody());
        postDao.save(postData);
        model.addAttribute("posts", postData);

        return "redirect:/posts";
    }


    @PostMapping("/posts/{n}/delete")
    public String deletePost(@PathVariable long n, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postDao.findById(n).get();
        if(user.getId() == post.getUser().getId()){
            postDao.deleteById(n);
        }
        System.out.println(user.getId());
        System.out.println(post.getUser().getId());
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;     }

    @GetMapping("/posts/{id}")
    public String findPostById(@PathVariable long id, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUserData = usersDao.findById(currentUser.getId());
        model.addAttribute("user", currentUserData);
        Post post = postDao.findById(id).get();
        if (post.getId() == null) {
            return "posts/index";
        }
        model.addAttribute("post", post);
        return "posts/show";
    }

    @PostMapping("/posts/{id}/like")
    public String likePost(@PathVariable Long id, Principal principal, HttpServletRequest request) {
        User user = usersDao.findByUsername(principal.getName());
        Post post = postDao.findById(id).get();

        if (post.hasLiked(user)) {
            postService.decrementLikes(id, user);
        } else {
            postService.incrementLikes(id, user);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;     }

    @PostMapping("/posts/{id}/profile-like")
    public String likePostProfile(@PathVariable Long id, Principal principal, HttpServletRequest request) {
        User user = usersDao.findByUsername(principal.getName());
        Post post = postDao.findById(id).get();

        if (post.hasLiked(user)) {
            postService.decrementLikes(id, user);
        } else {
            postService.incrementLikes(id, user);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;     }
}



