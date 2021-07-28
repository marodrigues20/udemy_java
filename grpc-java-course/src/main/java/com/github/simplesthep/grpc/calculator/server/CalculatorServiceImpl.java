package com.github.simplesthep.grpc.calculator.server;

import com.proto.calculator.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void sum(SumRequest request, StreamObserver<SumReponse> responseObserver) {

        SumReponse sumReponse = SumReponse.newBuilder()
                                          .setSumResult(request.getFirstNumber() + request.getSecondNumber())
                                          .build();

        // send the response
        responseObserver.onNext(sumReponse);

        // complete the RPC call
        responseObserver.onCompleted();
    }

    @Override
    public void primeNumberDecomposition(PrimeNumberDecompositionRequest request,
            StreamObserver<PrimeNumberDecompositionResponse> responseObserver) {

        Long number = request.getNumber();
        Integer divisor = 2;

        while (number > 1) {
            if (number % divisor == 0) {
                number = number / divisor;
                responseObserver.onNext(PrimeNumberDecompositionResponse.newBuilder()
                                                                        .setPrimeFactor(divisor)
                                                                        .build());
            } else {
                divisor = divisor + 1;
            }
        }

        // complete the RPC call
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ComputeAverageRequest> computeAverage(
            StreamObserver<ComputeAverageResponse> responseObserver) {


        StreamObserver<ComputeAverageRequest> requestObserver = new StreamObserver<ComputeAverageRequest>() {
            // running sum and count
            int sum = 0;
            int count = 0;

            @Override
            public void onNext(ComputeAverageRequest value) {
                // increment them sum
                sum += value.getNumber();
                // increment the count
                count += 1;
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                double average = (double) sum / count;
                responseObserver.onNext(ComputeAverageResponse.newBuilder()
                                                              .setAverage(average)
                                                              .build());

                responseObserver.onCompleted();
            }
        };

        return requestObserver;

    }

    @Override
    public StreamObserver<FindMaximumRequest> findMaximum(StreamObserver<FindMaximumResponse> responseObserver) {

        return new StreamObserver<FindMaximumRequest>() {

            int currentMaximum = 0;

            @Override
            public void onNext(FindMaximumRequest value) {
                int currentNumber = value.getNumber();

                if (currentNumber > currentMaximum) {
                    currentMaximum = currentNumber;
                    responseObserver.onNext(FindMaximumResponse.newBuilder()
                                                               .setMaximum(currentMaximum)
                                                               .build());
                } else {
                    // nothing
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onCompleted();
            }

            @Override
            public void onCompleted() {
                // send the current last maximum
                responseObserver.onNext(FindMaximumResponse.newBuilder()
                                                           .setMaximum(currentMaximum)
                                                           .build());
                // the server is done sending data
                responseObserver.onCompleted();
            }
        };

    }

    @Override
    public void squareRoot(SquareRootRequest request, StreamObserver<SquareRootResponse> responseObserver) {

        Integer number = request.getNumber();

        if (number >= 0) {
            double numberRoot = Math.sqrt(number);
            responseObserver.onNext(SquareRootResponse.newBuilder()
                                                      .setNumberRoot(numberRoot)
                                                      .build());
        } else {
            // we construct the exception
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("The Number being sent is not positive")
                            .augmentDescription("Number sent: " + number)
                                                            .asRuntimeException());
        }


    }
}
