package com.codeup.codeupspringblog.models;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length=50)
    private String title;
    @Column(nullable = false, length = 50)
    private String location_name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Timestamp event_DateAndTime;
    @Column(nullable = false)
    private String location_address;
    private LocalDateTime created_at;

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

    public Timestamp getEvent_DateAndTime() {
        return event_DateAndTime;
    }

    public void setEvent_DateAndTime(Timestamp event_DateAndTime) {
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
}
