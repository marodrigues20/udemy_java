package com.github.simplesthep.grpc.calculator.server;

import com.github.simplesthep.grpc.greeting.server.GreetServiceImpl;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Calculator gRPC");

        Server server = ServerBuilder.forPort(50051)
                                     .addService(new CalculatorServiceImpl())
                                     .addService(ProtoReflectionService.newInstance()) // enable reflection
                                     .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully stopped the server");
        }));

        server.awaitTermination();
    }
}
