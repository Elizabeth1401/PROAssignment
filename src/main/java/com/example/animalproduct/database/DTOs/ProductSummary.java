package com.example.animalproduct.database.DTOs;

import java.time.Instant; //couldnt figure out how to get time from database

public record ProductSummary(int productId, String productType, Instant createdAt) {}

