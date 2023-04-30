package com.capstone.furever.controllers;

import com.capstone.furever.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("email") String email,
                            @RequestParam("subject") String subject,
                            @RequestParam("message") String message,
                            Model model) {

        emailService.prepareAndSend(email, subject, "New message from website", message);

        model.addAttribute("message", "Your message has been sent successfully.");
        return "users/login";
    }
}
