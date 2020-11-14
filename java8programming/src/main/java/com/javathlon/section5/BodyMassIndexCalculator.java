package com.javathlon.section5;

public class BodyMassIndexCalculator {

    public static void main(String[] args){
        Exercise_Human myHumanInstance = new Exercise_Human();

        myHumanInstance.setWeight(80);
        myHumanInstance.setHeight(1.85);

        double result = myHumanInstance.calculateBodyMassIndex();

        System.out.println(myHumanInstance.findBodyType());

        System.out.println(result);
    }
}
