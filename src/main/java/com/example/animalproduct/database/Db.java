package com.example.animalproduct.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Db {
  private static final String URL =
      "jdbc:postgresql://localhost:5432/slaughterhouse?currentSchema=slaughterhouse";
  private static final String USER = "postgres"; //change to your user if you changed it
  private static final String PASS = "14012004"; //change to your password

  private Db() {}

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);
  }
}
