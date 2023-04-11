package com.codeup.codeupspringblog.repositories;

import com.codeup.codeupspringblog.models.Breed;
import com.codeup.codeupspringblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BreedRepository  extends JpaRepository <Breed, Long> {


//    Breed findBreedsByBreed_name(String breed_name);
}
