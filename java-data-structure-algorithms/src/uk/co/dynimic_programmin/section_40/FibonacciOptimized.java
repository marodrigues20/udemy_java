/**
 * Demonstrates the optimised Bottom-Up Dynamic Programming approach to computing Fibonacci numbers.
 *
 * <p><b>Approach:</b> Bottom-Up (Iterative) with constant space.
 * Instead of storing all intermediate results in an array (tabulation),
 * this approach uses only two variables to track the previous and current
 * Fibonacci values, discarding older values as they are no longer needed.
 *
 * <p><b>Key improvement over Iterative Tabulation:</b>
 * The array {@code int[] res} of size n+1 is replaced by just two integer variables
 * ({@code prev} and {@code curr}), reducing Space Complexity from O(n) to O(1).
 *
 * <p><b>Time Complexity: O(n)</b> — the loop runs from 3 to n, computing each
 * Fibonacci number exactly once.
 *
 * <p><b>Space Complexity: O(1)</b> — only three integer variables are used
 * ({@code prev}, {@code curr}, {@code result}), regardless of the size of n.
 */
public class FibonacciOptimized {

    static void main() {
        System.out.println(fib(7));
    }

    /**
     * Computes the nth Fibonacci number using optimised bottom-up iteration.
     *
     * <p>Uses only two variables to represent the sliding window of the two
     * most recent Fibonacci values. At each step, the oldest value is discarded
     * and replaced by the newest, moving the window forward by one position.
     *
     * <p><b>Base cases:</b> {@code fib(1) = 1} and {@code fib(2) = 1} are
     * handled explicitly before the loop, since the loop starts at index 3.
     *
     * @param n the position in the Fibonacci sequence (1-indexed)
     * @return the nth Fibonacci number
     */
    public static int fib(int n) {

        // Base cases: fib(1) and fib(2) are both 1
        if (n == 1 || n == 2) {
            return 1;
        }

        int prev = 1;   // represents fib(i-2)
        int curr = 1;   // represents fib(i-1)
        int result = 0; // represents fib(i)

        // Build up from fib(3) to fib(n) using only the two previous values
        for (int i = 3; i <= n; i++) {
            result = prev + curr; // fib(i) = fib(i-2) + fib(i-1)
            prev = curr;          // slide window: fib(i-2) becomes fib(i-1)
            curr = result;        // slide window: fib(i-1) becomes fib(i)
        }

        return result;
    }
}
