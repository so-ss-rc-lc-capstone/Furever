package com.codeup.codeupspringblog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.List;

public class Message {

    @JsonIgnore
    @ManyToMany()
    @JoinTable(
            name = "user_follows",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "followed_user_id")}
    )
    private List<User> messagingUsers;//we want to follow

    //Many to many received here
    @JsonIgnore
    @ManyToMany(mappedBy = "followedUsers")
    private List<User> messagedUser;

    @Column(columnDefinition = "LONGTEXT")
    private String bio;




}
