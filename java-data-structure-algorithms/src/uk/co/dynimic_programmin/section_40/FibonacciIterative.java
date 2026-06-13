package uk.co.dynimic_programmin.section_40;

/**
 * Demonstrates the Bottom-Up Dynamic Programming approach to computing Fibonacci numbers.
 *
 * <p><b>Approach:</b> Bottom-Up (Iterative).
 * Instead of starting from {@code fib(n)} and recursing downward (top-down),
 * this approach starts from the base cases {@code fib(0)} and {@code fib(1)}
 * and builds up the solution iteratively until reaching {@code fib(n)}.
 *
 * <p><b>Key difference from Memoization:</b> No recursion is used at all.
 * There is no call stack overhead. Results are stored in an array
 * and computed in order from index 0 up to n.
 *
 * <p><b>Time Complexity: O(n)</b> — the loop runs exactly n-1 times,
 * computing each Fibonacci number once.
 *
 * <p><b>Space Complexity: O(n)</b> — an array of size n+1 is allocated
 * to store all intermediate results from fib(0) to fib(n).
 *
 * <p><b>Optimisation opportunity:</b> Since each iteration only needs
 * the two previous values, the array can be replaced with two variables,
 * reducing Space Complexity to O(1).
 */
public class FibonacciIterative {

    static void main() {
        System.out.println(fib(7));
    }

    /**
     * Computes the nth Fibonacci number using bottom-up iteration.
     *
     * <p>Fills an array from index 0 upward, using previously stored
     * values to compute the next one. No recursion is involved.
     *
     * @param n the position in the Fibonacci sequence (0-indexed)
     * @return the nth Fibonacci number
     */
    public static int fib(int n) {

        int[] res = new int[n + 1];
        res[0] = 0; // Base case: fib(0) = 0
        res[1] = 1; // Base case: fib(1) = 1

        // Build up from fib(2) to fib(n), each value depends only on the two before it
        for (int index = 2; index <= n; index++) {
            res[index] = res[index - 1] + res[index - 2];
        }

        return res[n];
    }
}
