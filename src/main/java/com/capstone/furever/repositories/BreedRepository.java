package com.capstone.furever.repositories;

import com.capstone.furever.models.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository  extends JpaRepository <Breed, Long> {
}
