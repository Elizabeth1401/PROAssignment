package com.example.animalproduct.model;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Entity
public class Animal {
    @Id
    private String registrationNumber;

    private LocalDate date;
    private double weight;
    private String origin;
    @jakarta.persistence.Id
    private Long id;

    public Animal() {}

    public Animal(String registrationNumber, LocalDate date, double weight, String origin) {
        this.registrationNumber = registrationNumber;
        this.date = date;
        this.weight = weight;
        this.origin = origin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
