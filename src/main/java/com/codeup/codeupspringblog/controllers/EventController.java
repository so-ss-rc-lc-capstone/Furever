package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Event;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.EventRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;

@Controller

public class EventController {
    private final EventRepository eventsDao;
    private final UserRepository usersDao;
    private final EventService eventService;

    public EventController(EventRepository eventsDao, UserRepository usersDao, EventService eventService) {
        this.eventsDao = eventsDao;
        this.usersDao = usersDao;
        this.eventService = eventService;
    }

    @GetMapping("/events/create")
    public String eventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event/create-event";
    }

    @PostMapping("/events/create")
    public String createEvent(@ModelAttribute Event event) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = usersDao.findById(currentUser.getId());
        event.setUser(userData);
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
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = currentUser.getId();

        Event event = eventsDao.findById(id).get(); // Getting data from the database first
        Long eventId = event.getUser().getId();

        System.out.println(currentUserId);
        System.out.println(eventId);
        if(currentUserId == eventId){
        model.addAttribute("event", event);
            return "event/edit";
        }else {
            return "redirect:/events";
        }
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
        eventEdited.setEventPhoto(event.getEventPhoto());
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
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentUser.getId();

        Event event = eventsDao.findById(id).get(); // Getting data from the database first
        long eventId = event.getUser().getId();

        if(currentUserId == eventId){
            eventsDao.deleteById(id);
        }else{
            System.out.println("IDs do not match.");
        }
        return "redirect:/events";
    }

//    Event participation Mapping method
@PostMapping("/events/{id}/participate")
public String participateEvent(@PathVariable Long id, Principal principal) {
    User user = usersDao.findByUsername(principal.getName());
    Event event = eventsDao.findById(id).get();
    System.out.println("User participation: " + event.hasParticipated(user));

//
//    Event event = eventsDao.findById(id).get();
    System.out.println("Event participations: " + event.getParticipations());
//    User user = usersDao.findByUsername(principal.getName());
    System.out.println("Current user: " + user);
    System.out.println("User participation: " + event.hasParticipated(user));


//

    if(event.hasParticipated(user)){
        System.out.println("participated.");
        eventService.decrementParticipations(id, user);
    }else{
        System.out.println("Not participated.");
        eventService.incrementParticipations(id, user);
    }
    return "redirect:/events";
}

}

