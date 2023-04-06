package com.codeup.codeupspringblog.models;

import com.codeup.codeupspringblog.models.Post;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "users")
public class User {
    public User(User copy){
        id = copy.id; // copy users from database
        email = copy.email;
        username = copy.username;
        password = copy.password;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 50)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private List<Post> posts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "username='" + username + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }

    public static void main(String[] args) {
        User user = new User("osj3693","osj3693@gmail.com","1234");
        User user2 = new User(user);
        System.out.println(user);
        System.out.println(user2); // has the same value, but different tag number
    }
}