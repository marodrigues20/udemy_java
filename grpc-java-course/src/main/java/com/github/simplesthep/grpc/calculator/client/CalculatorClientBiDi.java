package com.github.simplesthep.grpc.calculator.client;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.FindMaximumRequest;
import com.proto.calculator.FindMaximumResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CalculatorClientBiDi {

    public static void main(String[] args) {
        System.out.println("Hello I'm a gRPC client");

        CalculatorClientBiDi main = new CalculatorClientBiDi();
        main.run();
    }

    private void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                                                      .usePlaintext()
                                                      .build();



        doBidiStreamingCall(channel);




        System.out.println("Shutting down channel");
        channel.shutdown();

    }

    private void doBidiStreamingCall(ManagedChannel channel){
        CalculatorServiceGrpc.CalculatorServiceStub asyncClient = CalculatorServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);


        StreamObserver<FindMaximumRequest> requestObserver = asyncClient.findMaximum(new StreamObserver<FindMaximumResponse>() {
            @Override
            public void onNext(FindMaximumResponse value) {
                System.out.println("Got new maximum from Server: " + value.getMaximum());
            }

            @Override
            public void onError(Throwable t) {
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Server is done sending messages");
            }
        });


        Arrays.asList(3, 5, 17, 9, 8, 30, 12).forEach(
                number -> {
                    System.out.println("Sending number: " + number);
                    requestObserver.onNext(FindMaximumRequest.newBuilder()
                                                             .setNumber(number)
                                                             .build());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        requestObserver.onCompleted();

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
