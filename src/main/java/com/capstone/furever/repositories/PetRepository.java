package com.capstone.furever.repositories;

import com.capstone.furever.models.Pet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
