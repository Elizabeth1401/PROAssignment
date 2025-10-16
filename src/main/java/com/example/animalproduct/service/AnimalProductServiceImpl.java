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

  @Override public void getAnimalsByProduct(ProductRequest req, StreamObserver<AnimalListResponse> out)
  {
    try
    {
      String productId = String.valueOf(req.getProductId());
      List<String> animals = dbManager.getAnimalsByProduct(productId);

      AnimalListResponse response = AnimalListResponse.newBuilder().addAllRegistrationNumbers(animals).build();

      out.onNext(response);
      out.onCompleted();
    }
    catch (SQLException e)
    {
      out.onError(io.grpc.Status.INTERNAL.withDescription("Database error: " + e.getMessage()).asRuntimeException());
    }
  }

  @Override public void getProductsByAnimal(AnimalRequest req, StreamObserver<ProductListResponse> out)
  {
    try
    {
      String registrationNumber = req.getRegistrationNumber();
      List<String> products = dbManager.getProductsByAnimal(registrationNumber);

      ProductListResponse response = ProductListResponse.newBuilder().addAllProductIds(products).build();

      out.onNext(response);
      out.onCompleted();
    }
    catch (SQLException e)
    {
      out.onError(io.grpc.Status.INTERNAL.withDescription("Database error: " + e.getMessage()).asRuntimeException());
    }
  }
}
