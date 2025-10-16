package com.example.animalproduct.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager
{
  private final String connectionUrl;

  public DatabaseManager(String connectionUrl)
  {
    this.connectionUrl = connectionUrl;
  }

  public List<String> getAnimalsByProduct(String productId) throws SQLException
  {
    List<String> animals = new ArrayList<>();

    String query = """
        SELECT a.registration_number
        FROM animals a
        JOIN animal_products ap ON a.id = ap.animal_id
        JOIN products p ON p.id = ap.product_id
        WHERE p.product_id = ?
        """;

    try (Connection conn = DriverManager.getConnection(connectionUrl);
        PreparedStatement stmt = conn.prepareStatement(query))
    {
      stmt.setString(1, productId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next())
      {
        animals.add(rs.getString("registration_number"));
      }
    }
    return animals;
  }

  public List<String> getProductsByAnimal(String registrationNumber) throws SQLException
  {
    List<String> products = new ArrayList<>();

    String query = """
        SELECT p.product_id
        FROM products p
        JOIN animal_products ap ON p.id = ap.product_id
        JOIN animals a ON a.id = ap.animal_id
        WHERE a.registration_number = ?
        """;

    try (Connection conn = DriverManager.getConnection(connectionUrl);
        PreparedStatement stmt = conn.prepareStatement(query))
    {

      stmt.setString(1, registrationNumber);
      ResultSet rs = stmt.executeQuery();

      while (rs.next())
      {
        products.add(rs.getString("product_id"));
      }
    }
    return products;
  }
}
