package com.example.animalproduct.database;

import com.example.animalproduct.database.DTOs.AnimalSummary;
import com.example.animalproduct.database.DTOs.ProductSummary;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class PostgresSlaughterhouseDao implements SlaughterhouseDao {

  private static final String SQL_ANIMALS_BY_PRODUCT = """
        SELECT DISTINCT a.animal_id, a.species
        FROM product_tray pt
        JOIN tray_part tp ON tp.tray_id = pt.tray_id
        JOIN part p       ON p.part_id  = tp.part_id
        JOIN animal a     ON a.animal_id = p.animal_id
        WHERE pt.product_id = ?
        ORDER BY a.animal_id
        """;

  private static final String SQL_PRODUCTS_BY_ANIMAL = """
        SELECT DISTINCT pr.product_id, pr.product_type, pr.created_at
        FROM part p
        JOIN tray_part tp    ON tp.part_id  = p.part_id
        JOIN product_tray pt ON pt.tray_id  = tp.tray_id
        JOIN product pr      ON pr.product_id = pt.product_id
        WHERE p.animal_id = ?
        ORDER BY pr.product_id
        """;

  private static final String SQL_INSERT_PRODUCT = """
        INSERT INTO product (product_type) VALUES ('half_animal') RETURNING product_id
        """;

  private static final String SQL_INSERT_PRODUCT_HALF = """
        INSERT INTO product_half_animal (product_id, animal_species) VALUES (?, ?)
        """;

  private static final String SQL_LINK_PRODUCT_TRAY = """
        INSERT INTO product_tray (product_id, tray_id) VALUES (?, ?)
        """;

  @Override
  public List<AnimalSummary> findAnimalsByProductId(int productId) throws SQLException {
    try (Connection con = Db.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL_ANIMALS_BY_PRODUCT)) {

      ps.setInt(1, productId);

      List<AnimalSummary> result = new ArrayList<>();
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          int id = rs.getInt("animal_id");
          String species = rs.getString("species");
          result.add(new AnimalSummary(id, species));
        }
      }
      return result;
    }
  }

  @Override
  public List<ProductSummary> findProductsByAnimalId(int animalId) throws SQLException {
    try (Connection con = Db.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL_PRODUCTS_BY_ANIMAL)) {

      ps.setInt(1, animalId);

      List<ProductSummary> result = new ArrayList<>();
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          int id = rs.getInt("product_id");
          String type = rs.getString("product_type");
          Timestamp ts = rs.getTimestamp("created_at");
          result.add(new ProductSummary(id, type,
              ts.toInstant().atOffset(ZoneOffset.UTC).toInstant())); //brainboom, need to remember
        }
      }
      return result;
    }
  }

  @Override
  public int createHalfAnimalProduct(String species, List<Integer> trayIds) throws SQLException {
    try (Connection con = Db.getConnection()) {
      con.setAutoCommit(false); //need to do when multiple related operations
      try {
        int productId;
        try (PreparedStatement ps = con.prepareStatement(SQL_INSERT_PRODUCT)) {
          try (ResultSet rs = ps.executeQuery()) {
            rs.next();
            productId = rs.getInt(1);
          }
        }
        try (PreparedStatement ps = con.prepareStatement(SQL_INSERT_PRODUCT_HALF)) {
          ps.setInt(1, productId);
          ps.setString(2, species);
          ps.executeUpdate();
        }
        try (PreparedStatement ps = con.prepareStatement(SQL_LINK_PRODUCT_TRAY)) {
          for (Integer trayId : trayIds) {
            ps.setInt(1, productId);
            ps.setInt(2, trayId);
            ps.addBatch();
          }
          ps.executeBatch();
        }

        con.commit();
        return productId;
      } catch (SQLException ex) {
        con.rollback();
        throw ex;
      } finally {
        con.setAutoCommit(true);
      }
    }
  }
}
