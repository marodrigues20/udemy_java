package com.github.simplesthep.grpc.calculator.client;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.PrimeNumberDecompositionRequest;
import com.proto.calculator.SumReponse;
import com.proto.calculator.SumRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClientStreamServer {

    public static void main(String[] args) {

        System.out.println("Hello I'm Calculator gRPC client - Server Stream");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                                      .usePlaintext()
                                                      .build();


        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        // Streaming Server
        Long  number = 5678905435434534534L;

        stub.primeNumberDecomposition(PrimeNumberDecompositionRequest.newBuilder()
                                                                     .setNumber(number)
                                                                     .build())
        .forEachRemaining(primeNumberDecompositionResponse -> {
            System.out.println(primeNumberDecompositionResponse.getPrimeFactor());
        });

        // do something
        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
