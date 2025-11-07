package com.example.animalproduct.controller;

import com.example.animalproduct.database.DTOs.AnimalDTO;
import com.example.animalproduct.model.Animal;
import com.example.animalproduct.service.AnimalMapper;
import com.example.animalproduct.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    public AnimalController(AnimalService animalService, AnimalMapper animalMapper) {
        this.animalService = animalService;
        this.animalMapper = animalMapper;
    }

    @PostMapping
    public ResponseEntity<AnimalDTO> registerAnimal(@RequestBody AnimalDTO animalDTO){
        Animal savedEntity = animalService.registerAnimal(animalMapper.toEntity(animalDTO));
        return ResponseEntity.ok(animalMapper.toAnimalDTO(savedEntity));
    }

    @GetMapping("/{registrationNumber}")
    public ResponseEntity<AnimalDTO> getAnimal(@PathVariable String registrationNumber){
        return animalService.getAnimal(registrationNumber)
                .map(animalMapper::toAnimalDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/data/{data}")
    public List<AnimalDTO> getAnimalByData(@PathVariable String data){
        return animalService.getAnimalsByDate(LocalDate.parse(data))
                .stream()
                .map(animalMapper::toAnimalDTO)
                .toList();
    }

    @GetMapping("/origin/{origin}")
    public List<AnimalDTO> getAnimalByOrigin(@PathVariable String origin){
        return animalService.getAnimalsByOrigin(origin)
                .stream()
                .map(animalMapper::toAnimalDTO)
                .toList();
    }
}
