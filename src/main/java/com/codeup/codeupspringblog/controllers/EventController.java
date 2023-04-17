package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Event;
import com.codeup.codeupspringblog.models.EventParticipation;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.EventRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import jakarta.mail.Address;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

//Original code for /events
//    @GetMapping("/events")
//    public String allEvents(Model model) {
//        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User userData = usersDao.findById(currentUser.getId());
//        model.addAttribute("user", userData);
//        List<Event> events = eventsDao.findAll();
//        model.addAttribute("events", events);
//        return "event/index";
//    }


    // Added for Participants modal
    @GetMapping("/events/{eventId}/participants")
    @ResponseBody
    public List<User> getEventAttendees(@PathVariable long eventId) {
        Event event = eventsDao.findById(eventId).orElse(null);
        if (event != null) {
            List<EventParticipation> participations = event.getParticipations();
            List<User> attendees = new ArrayList<>();
            for (EventParticipation participation : participations) {
                attendees.add(participation.getUser());
            }
            return attendees;
        }
        return null;
    }


    //New Code for the formatted date
    @GetMapping("/events")
    public String allEvents(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = usersDao.findById(currentUser.getId());
        model.addAttribute("user", userData);


        List<User> followedUsers = userData.getFollowedUsers();
        List<User> usersNotFollowing = new ArrayList<>();


        List<User> users = usersDao.findAll();
        List<Event> events = eventsDao.findAll();
        model.addAttribute("followedUsers",followedUsers);


        for(int i=0; i<users.size(); i++){
            System.out.println("[User]:"+ users.get(i).getId());
            if(followedUsers.contains(users.get(i))){
                System.out.println("[[already following!!!]]");
            }else{
                System.out.println("[[Not following!!!]]");
                usersNotFollowing.add(usersDao.findById(users.get(i).getId()));
            }
        }
        model.addAttribute("usersNotFollowing", usersNotFollowing);


//        for(int i=0; i<followedUsers.size(); i++){
//            System.out.println("[Followed User]:"+ followedUsers.get(i).getId());
////            if(followedUsers.get(i).getId()){
////                System.out.println("[[already following!!!]]");
////            }else{
////                System.out.println("[[Not following!!!]]");
////
////            }
//        }

//        System.out.println("Followed Users "+followedUsers);

        model.addAttribute("users",users);
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
        List<EventParticipation> participations = event.getParticipations();
        List<User> participants = new ArrayList<>();
        for (EventParticipation participation : participations) {
            participants.add(participation.getUser());
        }
        model.addAttribute("participants", participants);
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

    @GetMapping("events/{id}/delete-profile-event")
    public String deleteProfileEvent(@PathVariable long id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentUser.getId();

        Event event = eventsDao.findById(id).get(); // Getting data from the database first
        long eventId = event.getUser().getId();

        if(currentUserId == eventId){
            eventsDao.deleteById(id);
        }else{
            System.out.println("IDs do not match.");
        }
        return "redirect:/profile";
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

