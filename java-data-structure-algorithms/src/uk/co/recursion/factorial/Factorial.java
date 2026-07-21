package uk.co.recursion.factorial;

public class Factorial {


    static void main() {
        int myFac = factorial(4);
        System.out.println(myFac);
    }

    private static int factorial(int n) {

        if (n == 1) return 1;

        return n * factorial(n - 1);
    }


}
