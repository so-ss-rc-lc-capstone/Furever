package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.config.TalkJSConfig;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {



    private final UserRepository userDao;


    public ChatController(UserRepository userDao) {
        this.userDao = userDao;
    }

//    @Autowired
//    private TalkJSConfig talkJSConfig;
    @GetMapping("/chat/{selectedId}")
    public User chattingPage(Model model, @PathVariable Long selectedId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User currentUserData = userDao.findById(currentUser.getId());
        model.addAttribute("user",currentUserData);

        User otherUserData = userDao.findById(selectedId).get();
        model.addAttribute("otherUser", otherUserData);


        return userDao.findById(selectedId).get();
//        return "users/friend";
    }

//    @Autowired
//    private TalkJSConfig talkJSConfig;

//    @GetMapping("/chat/{conversationId}/{otherUserId}")
//    public String chatPage(Model model, @PathVariable String conversationId, @PathVariable String otherUserId) {
//        String currentUserId = "12345"; // replace with current user's ID
//        String currentUserDisplayName = "Current User"; // replace with current user's display name
//        String currentUserEmail = "currentuser@example.com"; // replace with current user's email
//
//        model.addAttribute("currentUserId", currentUserId);
//        model.addAttribute("currentUserDisplayName", currentUserDisplayName);
//        model.addAttribute("currentUserEmail", currentUserEmail);
//        model.addAttribute("conversationId", conversationId);
//        model.addAttribute("otherUserId", otherUserId);
//
//        return "chat";
//
//    }
//    @GetMapping("/talkjs/config")
//    @ResponseBody
//    public Map<String, String> talkJSConfig() {
//        Map<String, String> config = new HashMap<>();
//        config.put("appId", talkJSConfig.getAppId());
//        config.put("meId", "12345"); // replace with current user's ID
//        config.put("meName", "Current User"); // replace with current user's display name
//        config.put("meEmail", "currentuser@example.com"); // replace with current user's email
//        config.put("mePhoto", "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200");
//        return config;
//    }
}



