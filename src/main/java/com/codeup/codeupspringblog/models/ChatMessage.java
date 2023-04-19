package com.codeup.codeupspringblog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChatMessage {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String content;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "sender_id")
   private User sender;

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(
           name = "message_user",
           joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
   )
   private List<User> receivers = new ArrayList<>();

}
