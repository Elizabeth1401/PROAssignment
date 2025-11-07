package com.example.animalproduct.database.DTOs;

import java.time.LocalDate;

public record AnimalDTO(String registrationNumber, LocalDate date, double weight, String origin) {
}
