package com.codeup.codeupspringblog.repositories;

import com.codeup.codeupspringblog.models.Pet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {


}
