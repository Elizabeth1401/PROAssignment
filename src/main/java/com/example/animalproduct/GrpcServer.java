package com.example.animalproduct;

import com.example.animalproduct.database.DatabaseManager;
import com.example.animalproduct.service.AnimalProductServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer
{
  public static void main(String[] args) throws Exception
  {
    DatabaseManager dbManager = new DatabaseManager("database url here");
    Server server = ServerBuilder.forPort(9090)
        .addService(new AnimalProductServiceImpl(dbManager))
        .build()
        .start();
    System.out.println("gRPC server started on : 9090");
    server.awaitTermination();
  }
}
