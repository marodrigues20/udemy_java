package level_01;

import java.util.List;
import java.util.stream.Stream;

public class Fibonacci {


    public static void main(String[] args) {

        //System.out.println(fibInteractive(3));
        System.out.println(fibRecursion2(3));
    }

    private static int fibInteractive(int number){

        if(number < 0) throw new IllegalArgumentException("number must be > 0");

        if(number == 0) return 0;
        if(number == 1) return 1;

        int prev = 0;
        int current = 1;
        int next = 0;

        for(int i = 2; i <= number; i++){

            next = prev + current;
            prev = current;
            current = next;

        }

        return current;

    }


    private static int fibRecursion(int number){

        if(number == 0) return 0;
        if(number == 1) return 1;

        return fibRecursion(number - 1) + fibRecursion(number - 2);

    }


    private static int fibRecursion2(int number){

        if(number == 0) return 0;
        if(number == 1) return 1;

        int prev = fibRecursion2(number - 1);
        int curr = fibRecursion2(number - 2);

        System.out.println("prev: " + prev + " curr: " + curr);

        return prev + curr;

    }





        public static List<Integer> fib(int n) {
            return Stream.iterate(
                            new int[]{0, 1}, arr -> new int[]{arr[1], arr[0] + arr[1]} // próxima geração
                    )
                    .limit(n)
                    .map(arr -> arr[0])
                    .toList();
        }


}
