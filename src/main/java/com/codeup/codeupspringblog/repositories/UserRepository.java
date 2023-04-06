package com.codeup.codeupspringblog.repositories;


import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    User findByUsername(String username);
}
