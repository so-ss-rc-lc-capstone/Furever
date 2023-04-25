package com.capstone.furever.controllers;

import com.capstone.furever.models.Breed;
import com.capstone.furever.models.Pet;
import com.capstone.furever.models.User;
import com.capstone.furever.repositories.BreedRepository;
import com.capstone.furever.repositories.PetRepository;
import com.capstone.furever.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PetController {

    private final UserRepository userDao;
    private final PetRepository petsDao;
    private final BreedRepository breedsDao;

    public PetController(UserRepository userDao, PetRepository petsDao, BreedRepository breedsDao) {
        this.userDao = userDao;
        this.petsDao = petsDao;
        this.breedsDao = breedsDao;
    }

    @GetMapping("/pets/register")
    public String showPetRegistrationForm(Model model) {

        model.addAttribute("pet", new Pet());
        System.out.println("pet model created");

        List<Breed> breedNames = breedsDao.findAll();
        model.addAttribute("breeds", breedNames);
        return "pets/pet-register";
    }

    @PostMapping("/pets/register")
    public String registerPet(@ModelAttribute Pet pet) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(currentUser.getId());

        pet.setUser(userData);
        petsDao.save(pet);
        return "redirect:/profile";
    }

    @GetMapping("/pets/card")
    public String getPetIndexPage(Model model) {

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userData = userDao.findById(currentUser.getId());
        model.addAttribute("user", userData);

        List<Pet> pets = petsDao.findAll();
        System.out.println(pets.get(0).getUser().getUsername());
        model.addAttribute("pets", pets);
        return "pets/pet-card";
    }

    @GetMapping("/pets/{id}")
    public String findPetById(@PathVariable long id, Model model) {
        Pet petData = petsDao.findById(id).get();
        if (petData.getId() == 0) {
            return "users/profile";
        }
        model.addAttribute("pet", petData);

        return "pets/pet-show";
    }

    @GetMapping("/pets/{id}/edit")
    public String showPetEdit(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pet petData = petsDao.findById(id).get(); // Getting data from the database first

        List<Breed> breedNames = breedsDao.findAll();
        model.addAttribute("breeds", breedNames);

        if (user.getId() == petData.getUser().getId()) {
            model.addAttribute("pet", petData);
            return "pets/pet-edit";
        }
        return "redirect:/profile";
    }

    @PostMapping("/pets/{id}/edit")
    public String editPet(@ModelAttribute Pet pet, @PathVariable Long id, Model model) {
        Pet petsData = petsDao.findById(id).get(); // Getting data from the database first

        petsData.setPetname(pet.getPetname());
        petsData.setDateOfBirth(pet.getDateOfBirth());
        petsData.setGender(pet.getGender());
        petsData.setImage(pet.getImage());
        petsDao.save(petsData);
        model.addAttribute("pets", petsData);
        return "redirect:/profile"; // go to controller
    }

    @GetMapping("/pets/{n}/delete")
    public String deletePet(@PathVariable long n) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pet petData = petsDao.findById(n).get();
        if (user.getId() == petData.getUser().getId()) {
            petsDao.deleteById(n);
        }
        return "redirect:/profile";
    }
}
