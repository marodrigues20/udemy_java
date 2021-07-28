package com.github.simplesthep.grpc.calculator.client;

import com.proto.calculator.Calculator;
import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.SumReponse;
import com.proto.calculator.SumRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {

    public static void main(String[] args) {

        System.out.println("Hello I'm Calculator gRPC client");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                                      .usePlaintext()
                                                      .build();

        System.out.println("Creating stub");
        // Create a calculator service client (blocking - synchronous)
        CalculatorServiceGrpc.CalculatorServiceBlockingStub calculatorClient = CalculatorServiceGrpc.newBlockingStub(channel);

        SumRequest sumRequest = SumRequest.newBuilder()
                .setFirstNumber(3)
                .setSecondNumber(5)
                .build();

        // call the RPC and get back a SumResponse (protocol buffers)
        SumReponse calculatorResponse = calculatorClient.sum(sumRequest);

        System.out.println(calculatorResponse.getSumResult());

        // do something
        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
