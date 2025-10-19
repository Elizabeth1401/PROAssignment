package com.example.animalproduct.database;

import com.example.animalproduct.database.DTOs.AnimalSummary;
import com.example.animalproduct.database.DTOs.ProductSummary;

import java.sql.SQLException;
import java.util.List;

public interface SlaughterhouseDao
{
  List<AnimalSummary> findAnimalsByProductId(int productId) throws SQLException;
  List<ProductSummary> findProductsByAnimalId(int animalId) throws SQLException;
  int createHalfAnimalProduct(String species, List<Integer> trayIds) throws SQLException;
}
