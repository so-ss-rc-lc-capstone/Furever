package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Pet;
import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PetRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PetController {

    private final UserRepository userDao;
    private final PetRepository petsDao;

    public PetController(UserRepository userDao, PetRepository petsDao) {
        this.userDao = userDao;
        this.petsDao = petsDao;
    }


//    @GetMapping("/pets")
//    public String getPetIndexPage(Model model){
//
//        List<Pet> pets = petsDao.findAll();
//        model.addAttribute("pets", pets);
//        return "posts/index";
//    }

    @GetMapping("/pets/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("pet", new Pet());
        System.out.println("pet model created");
        return "pets/pet-register";
    }

    @PostMapping("/pets/register")
    public String registerUser(@ModelAttribute Pet pet){

        petsDao.save(pet);
        return "users/profile";
    }





    // edited Pet
}
