package com.github.simplesteph.protobuf;

import example.complex.Complex.*;
import java.util.Arrays;

public class ComplexMain {

    public static void main(String[] args) {

        System.out.println("Complex Example");

        DummyMessage oneDummy = newDummyMessage(55, "one dummy message");


        ComplexMessage.Builder builder = ComplexMessage.newBuilder();
        // a singular message field
        builder.setOneDummy(oneDummy);

        //repeated fields
        builder.addMultipleDummy(newDummyMessage(66, "second dummuy"));
        builder.addMultipleDummy(newDummyMessage(67, "third dummuy"));
        builder.addMultipleDummy(newDummyMessage(68, "fourth dummuy"));

        builder.addAllMultipleDummy(Arrays.asList(
                newDummyMessage(69, "other dummuy"),
                newDummyMessage(70, "other other dummuy")
        ));


        ComplexMessage message = builder.build();

        System.out.println(message.toString());

        //GET EXAMPLE
        //message.getMultipleDummyList();



    }

    public static DummyMessage newDummyMessage(Integer id, String name){
        // Same learning as SimpleMain
        DummyMessage.Builder dummyMessageBuilder = DummyMessage.newBuilder();
        DummyMessage message = dummyMessageBuilder.setName(name)
                           .setId(id)
                           .build();

        return message;
    }



}
