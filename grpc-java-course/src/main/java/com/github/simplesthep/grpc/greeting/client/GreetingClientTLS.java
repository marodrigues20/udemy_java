package com.github.simplesthep.grpc.greeting.client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import java.io.File;
import javax.net.ssl.SSLException;

public class GreetingClientTLS {

    public static void main(String[] args) throws SSLException {
        System.out.println("Hello I'm gRPC client using TLS");

        GreetingClientTLS main = new GreetingClientTLS();
        main.run();
    }

    private void run() throws SSLException {

        // With server authentication SSL/TLS; custom CA root certificates; not on Android
        ManagedChannel securityChannel = NettyChannelBuilder.forAddress("localhost", 50051)
                                                    .sslContext(GrpcSslContexts.forClient().trustManager(new File("ssl/ca.crt")).build())
                                                    .build();


        doUnaryCall(securityChannel);

        System.out.println("Shutting down channel");
        securityChannel.shutdown();
    }

    private void doUnaryCall(ManagedChannel securityChannel) {

        // created a greet service client (blocking - synchronous)
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(securityChannel);

        // Unary
        // created a protocol buffer greeting message
        Greeting greeting = Greeting.newBuilder()
                                    .setFirstName("Stephane")
                                    .setLastName("Maarek")
                                    .build();

        // do the same for a GreetRequest
        GreetRequest greetRequest = GreetRequest.newBuilder()
                                                .setGreeting(greeting)
                                                .build();

        // call the RPC and get back a GreetResponse (protocol buffers)
        GreetResponse greetResponse = greetClient.greet(greetRequest);

        System.out.println(greetResponse.getResult());
    }
}
