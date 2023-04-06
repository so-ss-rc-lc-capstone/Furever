package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Like;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.models.Users;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import com.codeup.codeupspringblog.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Controller
public class PostController {


    // Dependency Injection
    private final PostRepository postDao;
    private final UserRepository userDao;

    private final EmailService emailService;

    private final PostService postService;


    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService, PostService postService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
        this.postService = postService;
    }




    @GetMapping("posts/create/cat")
    public String createCat(){
        User user = userDao.findById(1);

        Post product = new Post("Cat","said Meow", user);
        postDao.save(product);
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

        User userData = userDao.findById(currentUser.getId());
        post.setUser(userData);

//        User user = Users.randomUser(userDao);

        System.out.println("logged in ID: "+currentUser.getId());
        System.out.println("Post ID: " + post.getId());
        postDao.save(post);
        emailService.prepareAndSend(post);
        return "redirect:/posts"; // go to controller
//        return "<p>Post: "+name+"</p><p>Price: " +price+"</p>";
    }

//    @GetMapping("/posts/create")
//    public String postIndex(){
//        return "posts/create";
//    }

//
//    @PostMapping("/posts/create")
//    public String createPost(@RequestParam String title , @RequestParam String body){
//        System.out.println(title);
//        System.out.println(body);
//        User user = userDao.findById(1);
//        Post post = new Post(title,body,user);
//        postDao.save(post);
//        return "redirect:/posts/index"; // go to controller
////        return "<p>Post: "+name+"</p><p>Price: " +price+"</p>";
//    }


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
        Post editedPost = postDao.findById(id).get(); // Getting data from the database first

        editedPost.setTitle(post.getTitle());
        editedPost.setBody(post.getBody());
        postDao.save(editedPost);
        model.addAttribute("posts", editedPost);
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


    @GetMapping("/posts")
    public String getPostIndexPage(Model model){

        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
//        List<Post> filteredPostsList = posts
//                .stream()
//                .filter(product -> product.getPriceInCents()<1000)
//                .collect(Collectors.toList());
//        model.addAttribute("posts", filteredPostsList);

        return "posts/index";
    }

    @GetMapping("/posts/delete/{n}")
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
        User user = userDao.findByUsername(principal.getName());
        Post post = postDao.findById(id).get();
        System.out.println(post.hasLiked(user));
        if(post.hasLiked(user)){
            postService.decrementLikes(id, user);
        }else{
            postService.incrementLikes(id, user);
        }
        return "redirect:/posts";
    }

//    @PostMapping("/posts/{id}/unlike")
//    public String unlikePost(@PathVariable Long id, Principal principal) {
//        User user = userDao.findByUsername(principal.getName());
//        postService.decrementLikes(id, user);
//        return "redirect:/posts";
//    }




//    public void increment(Long id){
//        try {
//            String insertQuery = "UPDATE products SET quantity = quantity+1 WHERE id = ?";
//            PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
//            stmt.setLong(1, id);
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error inserting a product", e);
//        }
//    }
//
//
//    public void decrement(Long id){
//        try {
//            String insertQuery = "UPDATE products SET quantity = quantity-1 WHERE id = ? AND quantity>0";
//            PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
//            stmt.setLong(1, id);
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error inserting a product", e);
//        }
//    }


}



