package com.github.simplesthep.grpc.calculator.client;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.ComputeAverageRequest;
import com.proto.calculator.ComputeAverageResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CalculatorClientStreamClient {

    public static void main(String[] args) {
        System.out.println("Hello I'm a gRPC Streaming Client");
        CalculatorClientStreamClient main = new CalculatorClientStreamClient();
        main.run();
    }

    ManagedChannel channel;

    private void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                                      .usePlaintext()
                                                      .build();


        doChannelStreamingCall(channel);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

    private void doChannelStreamingCall(ManagedChannel channel) {

        CalculatorServiceGrpc.CalculatorServiceStub asyncClient = CalculatorServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<ComputeAverageRequest> requestObserver =
                asyncClient.computeAverage(new StreamObserver<ComputeAverageResponse>() {
            @Override
            public void onNext(ComputeAverageResponse value) {
                System.out.println("Received a response from the server");
                System.out.println(value.getAverage());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("Server has completed sending us data");
                latch.countDown();

            }
        });

        // we send 10000 messages to our server (client streaming)
        for( int i = 0; i < 10000; i ++){
            requestObserver.onNext(ComputeAverageRequest.newBuilder()
                                                        .setNumber(i)
                                                        .build());
        }

        // we expect the average to be 10 / 4 = 2.5

        requestObserver.onCompleted();
        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
