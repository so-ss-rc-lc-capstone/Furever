package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Event;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.EventRepository;
import com.codeup.codeupspringblog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void decrementParticipations(Long eventId, User user) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.removeParticipation(user);
        eventRepository.save(event);
    }

    public void incrementParticipations(Long eventId, User user) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.addParticipation(user);
        eventRepository.save(event);
    }

    //Event service for event likes

    public void incrementEventLikes(Long eventId, User user) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.addEventLike(user);
        eventRepository.save(event);
    }

    public void decrementEventLikes(Long eventId, User user) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.removeEventLike(user);
        eventRepository.save(event);
    }
}
