package com.example.service;

import com.example.grpc.*;
import io.grpc.stub.StreamObserver;

public class AnimalProductServiceImpl extends AnimalProductServiceGrpc.AnimalProductServiceImplBase
{
  @Override
  public void getAnimalsByProduct(ProductRequest req, StreamObserver<AnimalListResponse> out) {
    out.onNext(AnimalListResponse.newBuilder().build()); // placeholder TODO DB query
    out.onCompleted();
  }
  @Override
  public void getProductsByAnimal(AnimalRequest req, StreamObserver<ProductListResponse> out) {
    out.onNext(ProductListResponse.newBuilder().build()); // placeholder TODO DB query
    out.onCompleted();
  }
}
