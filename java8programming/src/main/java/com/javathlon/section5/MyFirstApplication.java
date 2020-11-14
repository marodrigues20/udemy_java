package com.javathlon.section5;

public class MyFirstApplication {

    public static void main(String[] args){

        ArrayOperation arrayOperation = new ArrayOperation();

        int myArray[] = new int[] {1,2,3,4,1,2,3,4};

        int mySecondArray[] = {-1,-2,-3,-4,-1,-2,-3,-4};

        int result = arrayOperation.sumItems(myArray);
        int result2 = arrayOperation.sumItems(mySecondArray);

        System.out.println(result);
        System.out.println(result2);

        double myDoubleArray[] = new double[] {-1.0,-6.0};

        arrayOperation.sumItems(myDoubleArray);



        Human man = new Human();
        Human woman = new Human();


        man.setName("Talha");

        woman.setName("Jennifer");

        System.out.println("Male name:" + man.name);

        System.out.println("Female name:" + woman.name);

        woman.setName("Nicole");

        System.out.println("Male name:" + man.name);

        System.out.println("Female name:" + woman.name);

        woman.height = 160;
        woman.weight = 60;

        String s = woman.sumarizeThePhysicalValues();
        System.out.println(s);

        man.height = 180;
        man.weight = 90;

        s = man.sumarizeThePhysicalValues();
        System.out.println(s);

        StringOperation stringOperation = new StringOperation();

        String[] stringArrayToConcatanate = new String[4];

        stringArrayToConcatanate[0] = "talha";
        stringArrayToConcatanate[1] = "acakci";
        stringArrayToConcatanate[2] = "developer";
        stringArrayToConcatanate[3] = "trainer";

        String concatanate = stringOperation.concatanateStrings(stringArrayToConcatanate);

        System.out.println(stringOperation.concatanateStringsWithVarargs("a","b","c"));
        System.out.println(concatanate);


    }
}
