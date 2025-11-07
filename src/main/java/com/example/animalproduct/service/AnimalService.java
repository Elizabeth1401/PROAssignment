package com.example.animalproduct.service;

import com.example.animalproduct.model.Animal;
import com.example.animalproduct.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal registerAnimal (Animal animal) {
        return animalRepository.save(animal);
    }

    public Optional<Animal> getAnimal(String registrationNumber){
        return animalRepository.findById(registrationNumber);
    }

    public List<Animal> getAnimalsByDate(LocalDate date){
        return animalRepository.findByDate(date);
    }

    public List<Animal> getAnimalsByOrigin(String origin){
        return animalRepository.findByOrigin(origin);
    }
}
