package com.github.simplesthep.grpc.greeting.client;

import com.proto.greet.GreetManyTimesRequest;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClientStreamServerSide {

    public static void main(String[] args) {

        System.out.println("Hello I'm gRPC client");


        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                                      .usePlaintext()
                                                      .build();

        System.out.println("Creating stub");


        // Create a greet service client (blocking - synchronous)
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // Server Streaming
        // we prepare the request
        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest.newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Alexandre"))
                .build();

        // we stream the response (in a blocking manner)
        greetClient.greetManyTimes(greetManyTimesRequest)
                   .forEachRemaining(greetManyTimesResponse -> {
                       System.out.println(greetManyTimesResponse.getResult());
                   });

        // do something
        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
