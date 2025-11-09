package com.example.animalproduct.service;

import com.example.animalproduct.database.DTOs.AnimalDTO;
import com.example.animalproduct.model.Animal;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper {

    public AnimalDTO toAnimalDTO(Animal entity){
        return new AnimalDTO(
                entity.getRegistrationNumber(),
                entity.getDate(),
                entity.getWeight(),
                entity.getOrigin()
        );
    }
    public Animal toEntity(AnimalDTO dto){
        Animal animal = new Animal();
        animal.setRegistrationNumber(dto.registrationNumber());
        animal.setDate(dto.date());
        animal.setWeight(dto.weight());
        animal.setOrigin(dto.origin());
        return animal;
    }
}
