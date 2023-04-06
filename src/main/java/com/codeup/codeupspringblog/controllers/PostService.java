package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public void incrementLikes(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.addLike(user);
        postRepository.save(post);
    }

    public void decrementLikes(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.removeLike(user);
        postRepository.save(post);
    }
}

