package com.codeup.codeupspringblog.repositories;

import com.codeup.codeupspringblog.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Long> {

//    LocalDateTime findCreatedAtById(Long id);
}
