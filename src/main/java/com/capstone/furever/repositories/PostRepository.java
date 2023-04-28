package com.capstone.furever.repositories;

import com.capstone.furever.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.capstone.furever.models.Comments;


public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Query("DELETE FROM Comments comment WHERE comment.post.id = :post_id")
    void deleteCommentsByPostId(@Param("post_id") Long post_id);
}
