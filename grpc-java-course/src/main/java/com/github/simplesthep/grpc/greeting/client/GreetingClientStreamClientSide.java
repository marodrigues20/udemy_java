package com.github.simplesthep.grpc.greeting.client;

import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import com.proto.greet.LongGreetRequest;
import com.proto.greet.LongGreetResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreetingClientStreamClientSide {

    public static void main(String[] args) {
        System.out.println("Hello I'm a gRPC Streaming Client");
        GreetingClientStreamClientSide main = new GreetingClientStreamClientSide();
        main.run();
    }


    private void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                                      .usePlaintext()
                                                      .build();


        doChannelStreamingCall(channel);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }


    private void doChannelStreamingCall(ManagedChannel channel) {
        // create an asynchronous client
        GreetServiceGrpc.GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<LongGreetRequest> requestObserver =
                asyncClient.longGreet(new StreamObserver<LongGreetResponse>() {
            @Override
            public void onNext(LongGreetResponse value) {
                // we get a response from the server
                System.out.println("Received a response from the server");
                System.out.println(value.getResult());
                // onNext will be called only once
            }

            @Override
            public void onError(Throwable t) {
                // we get an error from the server
            }

            @Override
            public void onCompleted() {
                // the server is done sending us data
                // onCompleted will be called right after onNext()
                System.out.println("Server has completed sending us something");
                latch.countDown();
            }
        });

        // streaming message #1
        System.out.println("sending message 1");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                                               .setGreeting(Greeting.newBuilder()
                                                                    .setFirstName("Stephane")
                                                                    .build())
                                               .build());

        // streaming message #2
        System.out.println("sending message 2");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                                               .setGreeting(Greeting.newBuilder()
                                                                    .setFirstName("John")
                                                                    .build())
                                               .build());

        // streaming message #3
        System.out.println("sending message 3");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                                               .setGreeting(Greeting.newBuilder()
                                                                    .setFirstName("Marc")
                                                                    .build())
                                               .build());

        // we tell the server that the client is done sending data
        requestObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
