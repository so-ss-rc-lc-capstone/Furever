package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Event;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.EventRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller

public class EventController {
    private final EventRepository eventsDao;

    public EventController(EventRepository eventsDao) {
        this.eventsDao = eventsDao;
    }

    @GetMapping("/events/create")
    public String eventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event/create-event";
    }

    @PostMapping("/events/create")
    public String createEvent(@ModelAttribute Event event) {
        event.setCreated_at(LocalDateTime.now());
        eventsDao.save(event);
        return "redirect:/events";
    }

    @GetMapping("/events")
    public String allEvents(Model model) {
        List<Event> events = eventsDao.findAll();
        model.addAttribute("events", events);
        return "event/index";
    }

    //Edit the event
    @GetMapping("/events/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        Event event = eventsDao.findById(id).get();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Post post = postDao.findById(id).get(); // Getting data from the database first

//        System.out.println(user.getId());
//        System.out.println(post.getUser().getId());
//        if(user.getId() == post.getUser().getId()){
        model.addAttribute("event", event);
        return "event/edit";
//        }else {
//            return "redirect:/posts";
    }

    //Edit event post method
    @PostMapping("/events/{id}/edit")
    public String editEvent(@ModelAttribute Event event, @PathVariable long id) {
        Event eventEdited = eventsDao.findById(id).get();
        eventEdited.setTitle(event.getTitle());
        eventEdited.setLocation_name(event.getLocation_name());
        eventEdited.setDescription(event.getDescription());
        eventEdited.setEvent_DateAndTime(event.getEvent_DateAndTime());
        eventEdited.setLocation_address(event.getLocation_address());
        eventEdited.setCreated_at(LocalDateTime.now());
        eventsDao.save(eventEdited);
        return "redirect:/events/" + id + "/find";
    }

    //Find an event by id and print it in a html file
    @GetMapping("/events/{id}/find")
    public String findEvent(@PathVariable long id, Model model) {
        Event event = eventsDao.findById(id).get();
        model.addAttribute("event", event);
        return "event/show";
    }

    @GetMapping("events/{id}/delete")
    public String deleteEvent(@PathVariable long id) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Event event = eventsDao.findById(id).get();
//        Long userId = user.getId();
//        System.out.println("User Id: "+ userId);
//        System.out.println("Posts user id: "+ post.getUser().getId());
//        if(userId == post.getUser().getId()){
        eventsDao.deleteById(id);
//        }
        return "redirect:/events";
    }
}

