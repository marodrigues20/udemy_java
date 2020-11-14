package com.javathlon.section5;

public class Exercise_Human {

    private double height;
    private double weight;

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double calculateBodyMassIndex(){
        return weight / (height * height);
    }

    public String findBodyType(){

        if(18.5 < weight / (height * height)){
            return "Thin";
        }else if(18.5 < (weight / (height * height)) && ( 25.0 < (weight / (height * height) ))){
            return "Normal";
        }else if((weight / (height * height) > 25.0)){
            return "Overweight";
        }else{
            return "indefined";
        }
    }


}
