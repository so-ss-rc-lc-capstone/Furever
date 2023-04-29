package com.capstone.furever.controllers;

import com.capstone.furever.models.Event;
import com.capstone.furever.models.EventParticipation;
import com.capstone.furever.models.Post;
import com.capstone.furever.models.User;
import com.capstone.furever.repositories.EventRepository;
import com.capstone.furever.repositories.PostRepository;
import com.capstone.furever.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class EventController {
    private final EventRepository eventsDao;
    private final UserRepository usersDao;
    private final EventService eventService;

    private final PostRepository postDao;

    public EventController(EventRepository eventsDao, UserRepository usersDao, EventService eventService, PostRepository postDao) {
        this.eventsDao = eventsDao;
        this.usersDao = usersDao;
        this.eventService = eventService;
        this.postDao = postDao;

    }

    //    When the below url entered, navigates to create-event.html
    @GetMapping("/events/create")
    public String eventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event/create-event";
    }

    //    Cotroller send the event information to the database and redirect to events page
    @PostMapping("/events/create")
    public String createEvent(@ModelAttribute Event event) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = usersDao.findById(currentUser.getId());
        event.setUser(userData);
        event.setCreated_at(LocalDateTime.now());
        eventsDao.save(event);
        return "redirect:/events";
    }

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
        List<User> followedUsers = userData.getFollowedUsers();
        List<User> users = usersDao.findAll();
        List<Event> events = eventsDao.findAll();
        Collections.reverse(events);
        List<User> usersNotFollowing = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            System.out.println("[User]:" + users.get(i).getId());
            if (followedUsers.contains(users.get(i))) {
                System.out.println("[[already following!!!]]");
            } else {
                System.out.println("[[Not following!!!]]");
                usersNotFollowing.add(usersDao.findById(users.get(i).getId()));
            }
        }
        model.addAttribute("user", userData);
        model.addAttribute("followedUsers", followedUsers);
        model.addAttribute("usersNotFollowing", usersNotFollowing);
        model.addAttribute("users", users);
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


        if (currentUserId == eventId) {
            model.addAttribute("event", event);
            return "event/edit";
        } else {
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
        return "redirect:/events";
    }

    //Find an event by id and print it in a html file
    @GetMapping("/events/{id}/find")
    public String findEvent(@PathVariable long id, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = usersDao.findById(currentUser.getId());
        model.addAttribute("user", userData);
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

        if (currentUserId == eventId) {
            eventsDao.deleteById(id);
        } else {
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

        if (currentUserId == eventId) {
            eventsDao.deleteById(id);
        } else {
            System.out.println("IDs do not match.");
        }
        return "redirect:/profile";
    }

    //    Event participation Mapping method
    @PostMapping("/events/{id}/participate")
    public String participateEvent(@PathVariable Long id, Principal principal) {
        User user = usersDao.findByUsername(principal.getName());
        Event event = eventsDao.findById(id).get();
        if (event.hasParticipated(user)) {
            eventService.decrementParticipations(id, user);
        } else {
            eventService.incrementParticipations(id, user);
        }
        return "redirect:/events";
    }

    //Event Likes below
    @PostMapping("/events/{id}/like")
    public String likeEvent(@PathVariable Long id, Principal principal) {

        User user = usersDao.findByUsername(principal.getName());
        Event event = eventsDao.findById(id).get();

        if (event.hasLikedEvent(user)) {
            eventService.decrementEventLikes(id, user);
        } else {
            eventService.incrementEventLikes(id, user);
        }
        return "redirect:/events";
    }
}

