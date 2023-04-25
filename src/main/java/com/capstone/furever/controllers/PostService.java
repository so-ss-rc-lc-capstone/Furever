package com.capstone.furever.controllers;

import com.capstone.furever.models.Post;
import com.capstone.furever.models.User;
import com.capstone.furever.repositories.PostRepository;
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

