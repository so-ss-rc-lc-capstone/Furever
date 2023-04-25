package com.capstone.furever.repositories;

import com.capstone.furever.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
