package com.javathlon.section5;

public class Human {

    int weight;

    int height;

    String skinColor;

    String name;

    static int count = 0;

    public static void incrementCountByOne(){
        count++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String sumarizeThePhysicalValues(){
        String summary = "Weight: " + weight + " kgs " + "height:" + height;
        return summary;
    }

    public Human(){
        incrementCountByOne();
    }

    public Human(int weight, int height, String skinColor){

    this.weight = weight;
    this.height = height;
    this.skinColor = skinColor;


    }
}
