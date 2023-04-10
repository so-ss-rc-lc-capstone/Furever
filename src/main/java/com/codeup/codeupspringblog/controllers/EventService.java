package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Event;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.EventRepository;
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
}
