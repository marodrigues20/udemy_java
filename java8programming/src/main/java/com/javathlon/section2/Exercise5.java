package com.javathlon.section2;

public class Exercise5 {

    public static void main(String[] args){

            Exercise5.methodExercise(true, false, false);

    }

    public static void methodExercise(boolean isBlond, boolean isGraduated, boolean isFemale){

        if (isBlond == true && isGraduated == true){
            System.out.println("male blond graduated student.");
        }else if (isBlond == true && isGraduated == false){
            System.out.println("male blond not graduated student.");
        } else if (isFemale == true && isGraduated == true ){
            System.out.println("a female brunette graduated student.");
        } else if (isFemale == true && isGraduated == false){
            System.out.println("a female brunette not graduated student.");
        }
    }

    public static void methodExercise2(boolean isBlond, boolean isGraduated, boolean isFemale){

        if (isFemale == true && isBlond != true && isGraduated == true){
            System.out.println("a female and she is graduated student.");
        }else if (isFemale == true && isBlond == true && isGraduated == true){
            System.out.println("a female brunette graduated student.");
        } else if (isFemale == true && isBlond == true && isGraduated != true ){
            System.out.println("a female brunette isn't graduated student.");
        } else if (isFemale != true && isBlond == true && isGraduated != true){
            System.out.println("a male blond and graduated student.");
        } else if (isFemale != true && isBlond != true && isGraduated == true) {
            System.out.println("a male not blond and graduated student.");
        } else if (isFemale != true && isBlond == true && isGraduated != true) {
            System.out.println("a male blond and graduated student.");
        }
    }
}
