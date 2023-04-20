package com.codeup.codeupspringblog.repositories;

import com.codeup.codeupspringblog.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
