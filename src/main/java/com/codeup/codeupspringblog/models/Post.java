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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String image;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id") //
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comments> comments = new ArrayList<>();


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public Post(String body, User user) {
        this.body = body;
        this.user = user;
    }

    public Post(Long id, String title, String body, User user) {
        this.id = id;
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