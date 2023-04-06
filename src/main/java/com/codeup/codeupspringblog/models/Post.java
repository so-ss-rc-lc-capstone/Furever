package com.codeup.codeupspringblog.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto incrementing ID
    private Long id;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id") //
    private User user;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();


    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

    public Post(Long id, String title, String body, User user) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.user = user;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", user=" + user +
                ", likes=" + likes +
                '}';
    }

    public void addLike(User user) {
        Like like = new Like();
        like.setPost(this);
        like.setUser(user);
        likes.add(like);
    }

    public void removeLike(User user) {
        likes.removeIf(like -> like.getUser().equals(user));
    }

    public boolean hasLiked(User user) {
        return likes.stream().anyMatch(like -> like.getUser().equals(user));
    }

    public int getLikeCount() {
        return likes.size();
    }


}