package com.example.animalproduct.client;

import com.example.grpc.AnimalProductServiceGrpc;
import com.example.grpc.ProductRequest;
import com.example.grpc.AnimalRequest;
import com.example.grpc.AnimalListResponse;
import com.example.grpc.ProductListResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalProductClient
{
    private static final Logger logger = Logger.getLogger(AnimalProductClient.class.getName());

    private final ManagedChannel channel;
    private final AnimalProductServiceGrpc.AnimalProductServiceBlockingStub blocking;

    public AnimalProductClient(String host, int port) {
      channel = ManagedChannelBuilder.forAddress(host, port)
          .useTransportSecurity()
          .build();
      blocking = AnimalProductServiceGrpc.newBlockingStub(channel);
      logger.info("Client initialized for " + host + ":" + port);
    }

    public void shutdown() throws InterruptedException {
      channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
      logger.info("Client shutdown completed");
    }

    public AnimalListResponse getAnimalsByProduct(int productId) {
      logger.info("Requesting animals for product ID: " + productId);
      ProductRequest request = ProductRequest.newBuilder()
          .setProductId(productId)
          .build();
      try {
        return blocking.withDeadlineAfter(10, TimeUnit.SECONDS)
            .getAnimalsByProduct(request);
      } catch (StatusRuntimeException e) {
        logger.log(Level.WARNING, "Failed: ", e.getStatus());
        throw e;
      }
    }

    public ProductListResponse getProductsByAnimal(int animalId) {
      logger.info("Requesting products for animal ID: " + animalId);
      AnimalRequest request = AnimalRequest.newBuilder()
          .setAnimalId(animalId)
          .build();
      try {
        return blocking.withDeadlineAfter(10, TimeUnit.SECONDS)
            .getProductsByAnimal(request);
      } catch (StatusRuntimeException e) {
        logger.log(Level.WARNING, "Failed: ", e.getStatus());
        throw e;
      }
    }
  }

