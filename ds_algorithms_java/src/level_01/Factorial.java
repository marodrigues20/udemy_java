package level_01;

public class Factorial {

    public static void main(String[] args){

        //int result = factorialCalc(4);
        long result = factorialRecur(5);
        System.out.println("Result: " + result);

    }



    private static int factorialCalc(int number){

        int result = 1;
        for(int i = number; i > 0; i--){

            System.out.print(i);
            if( i > 1 ) System.out.println(" x ");
            result *= i;
        }
        return result;
    }


    private static long factorialRecur(long number){

        if(number <= 1) return 1;

        return number * factorialRecur(number - 1);
    }



}