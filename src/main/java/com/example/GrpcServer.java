package com.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer
{
  public static void main(String[] args) throws Exception
  {
    Server server = ServerBuilder.forPort(9090)
        .addService(new com.example.service.AnimalProductServiceImpl())
        .build()
        .start();
    System.out.println("gRPC server started on : 9090");
    server.awaitTermination();
  }
}
