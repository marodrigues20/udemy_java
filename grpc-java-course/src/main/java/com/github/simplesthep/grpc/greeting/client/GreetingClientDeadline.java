package com.github.simplesthep.grpc.greeting.client;

import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.GreetWithDeadlineRequest;
import com.proto.greet.GreetWithDeadlineResponse;
import com.proto.greet.Greeting;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;

public class GreetingClientDeadline {

    public static void main(String[] args) {
        System.out.println("Hello I'm a gRPC Streaming Client");
        GreetingClientDeadline main = new GreetingClientDeadline();
        main.run();
    }

    ManagedChannel channel;

    private void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                                      .usePlaintext()
                                                      .build();


        doUnaryCallWithDeadline(channel);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

    private void doUnaryCallWithDeadline(ManagedChannel channel) {
        GreetServiceGrpc.GreetServiceBlockingStub blockingStub = GreetServiceGrpc.newBlockingStub(channel);

        // first call (3000 ms deadline)
        try {
            System.out.println("Sending a request with a deadline of 3000 ms");
            GreetWithDeadlineResponse response = blockingStub.withDeadline(Deadline.after(3000, TimeUnit.MILLISECONDS))
                                                             .greetWithDeadline(GreetWithDeadlineRequest.newBuilder()
                                                                                                        .setGreeting(
                                                                                                                Greeting.newBuilder()
                                                                                                                        .setFirstName(
                                                                                                                                "Alex")
                                                                                                                        )
                                                                                                        .build());

            System.out.println(response.getResult());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.DEADLINE_EXCEEDED) {
                System.out.println("Deadline has been exceeded, we don't want the response");
            } else {
                e.printStackTrace();
            }
        }

        // second call (100 ms deadline)
        try {
            System.out.println("Sending a request with a deadline of 100 ms");
            GreetWithDeadlineResponse response = blockingStub.withDeadline(Deadline.after(100, TimeUnit.MILLISECONDS))
                                                             .greetWithDeadline(GreetWithDeadlineRequest.newBuilder()
                                                                                                        .setGreeting(
                                                                                                                Greeting.newBuilder()
                                                                                                                        .setFirstName(
                                                                                                                                "Alex")
                                                                                                        )
                                                                                                        .build());

            System.out.println(response.getResult());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.DEADLINE_EXCEEDED) {
                System.out.println("Deadline has been exceeded, we don't want the response");
            } else {
                e.printStackTrace();
            }
        }




    }
}
