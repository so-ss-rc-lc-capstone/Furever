package com.codeup.codeupspringblog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    private LocalDateTime created_at;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "posts_id")
    private Post post;

    public Comments( ) {}

    public Comments(Comments comment) {
        this.content = comment.content;
        this.created_at = comment.created_at;
        this.user = comment.user;
        this.post = comment.post;
    }
    public Comments(String content, LocalDateTime created_at, User user, Post post) {
        this.content = content;
        this.created_at = created_at;
        this.user = user;
        this.post = post;
    }





    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
