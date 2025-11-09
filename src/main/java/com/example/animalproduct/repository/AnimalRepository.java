package com.example.animalproduct.repository;

import com.example.animalproduct.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, String> {
    List<Animal> findByDate (LocalDate date);
    List<Animal> findByOrigin (String origin);
}
