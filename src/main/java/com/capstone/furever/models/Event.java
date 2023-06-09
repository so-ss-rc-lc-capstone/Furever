package com.capstone.furever.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 50)
    private String location_name;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @Column(nullable = false)
    private LocalDateTime event_DateAndTime;
    @Column(nullable = false)
    private String location_address;

    private LocalDateTime created_at;
    private String eventPhoto;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id") //
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventParticipation> participations = new ArrayList<>();


    //Event like connection

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventsLike> eventLikes = new ArrayList<>();


    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEvent_DateAndTime() {
        return event_DateAndTime;
    }

    public void setEvent_DateAndTime(LocalDateTime event_DateAndTime) {
        this.event_DateAndTime = event_DateAndTime;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }


    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<EventParticipation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<EventParticipation> participations) {
        this.participations = participations;
    }

    public String getEventPhoto() {
        return eventPhoto;
    }

    public void setEventPhoto(String eventPhoto) {
        this.eventPhoto = eventPhoto;
    }



    // Added for participants modal
    public List<User> getAttendees() {
        return participations.stream()
                .map(EventParticipation::getUser)
                .collect(Collectors.toList());
    }

    public void addParticipation(User user) {
        EventParticipation participation = new EventParticipation();
        participation.setEvent(this);
        participation.setUser(user);
        this.participations.add(participation);
    }

    public void removeParticipation(User user) {
        participations.removeIf(participation -> participation.getUser().equals(user));
    }

    public boolean hasParticipated(User user) {
        return participations.stream().anyMatch(participation -> participation.getUser().equals(user));
    }

    public int getCount() {
        return participations.size();
    }

    //Events like
    @JsonIgnore
    public List<EventsLike> getEventLikes() {
        return eventLikes;
    }

    public void setEventLikes(List<EventsLike> eventLikes) {
        this.eventLikes = eventLikes;
    }

    public void addEventLike(User user) {
        EventsLike eventLike = new EventsLike();
        eventLike.setEvent(this);
        eventLike.setUser(user);
        eventLikes.add(eventLike);
    }

    public void removeEventLike(User user) {
        eventLikes.removeIf(eventLike -> eventLike.getUser().equals(user));
    }

    public boolean hasLikedEvent(User user) {
        return eventLikes.stream().anyMatch(eventLike -> eventLike.getUser().equals(user));
    }

    public int getEventLikeCount() {
        return eventLikes.size();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", location_name='" + location_name + '\'' +
                ", description='" + description + '\'' +
                ", event_DateAndTime=" + event_DateAndTime +
                ", location_address='" + location_address + '\'' +
                ", created_at=" + created_at +
                ", eventPhoto='" + eventPhoto + '\'' +
                ", user=" + user +
                ", participations=" + participations +
                ", eventLikes=" + eventLikes +
                '}';
    }
}
