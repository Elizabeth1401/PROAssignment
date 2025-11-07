package com.example.animalproduct.service;

import com.example.animalproduct.database.DatabaseManager;
import com.example.grpc.*;
import io.grpc.stub.StreamObserver;

import java.sql.SQLException;
import java.util.List;

public class AnimalProductServiceImpl extends AnimalProductServiceGrpc.AnimalProductServiceImplBase
{
  private final DatabaseManager dbManager;

  public AnimalProductServiceImpl(DatabaseManager dbManager)
  {
    this.dbManager = dbManager;
  }
  @Override
  public void getAnimalsByProduct(ProductRequest request, StreamObserver<AnimalListResponse> responseObserver) {
    try {
      String productId = String.valueOf(request.getProductId());
      List<String> registrationNumbers = dbManager.getAnimalsByProduct(productId);

      AnimalListResponse.Builder responseBuilder = AnimalListResponse.newBuilder();

      int idCounter = 1;
      for (String regNumber : registrationNumbers) {
        Animal animal = Animal.newBuilder()
                .setId(idCounter++)
                .setRegistrationNumber(regNumber)
                .build();
        responseBuilder.addAnimals(animal);
      }

      responseObserver.onNext(responseBuilder.build());
      responseObserver.onCompleted();

    } catch (SQLException e) {
      e.printStackTrace();
      responseObserver.onError(
              io.grpc.Status.INTERNAL
                      .withDescription("Database error while fetching animals: " + e.getMessage())
                      .asRuntimeException());
    }
  }
  @Override
  public void getProductsByAnimal(AnimalRequest request, StreamObserver<ProductListResponse> responseObserver) {
    try {

      String registrationNumber = String.valueOf(request.getAnimalId());
      List<String> productIds = dbManager.getProductsByAnimal(registrationNumber);

      ProductListResponse.Builder responseBuilder = ProductListResponse.newBuilder();

      for (String id : productIds) {
        Product product = Product.newBuilder()
                .setId(Integer.parseInt(id))
                .setName("Product " + id)
                .build();
        responseBuilder.addProducts(product);
      }

      responseObserver.onNext(responseBuilder.build());
      responseObserver.onCompleted();

    } catch (SQLException e) {
      e.printStackTrace();
      responseObserver.onError(
              io.grpc.Status.INTERNAL
                      .withDescription("Database error while fetching products: " + e.getMessage())
                      .asRuntimeException());
    }
  }
}
