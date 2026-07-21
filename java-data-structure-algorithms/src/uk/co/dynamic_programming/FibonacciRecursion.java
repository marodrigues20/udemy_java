package uk.co.dynamic_programming;

/**
 * Demonstrates the pure recursive approach to computing Fibonacci numbers.
 *
 * <p><b>Approach:</b> Top-Down (recursive decomposition).
 * Each call to {@code fib(n)} breaks the problem into two smaller subproblems:
 * {@code fib(n-1)} and {@code fib(n-2)}, until it reaches the base cases
 * ({@code n == 0} or {@code n == 1}).
 *
 * <p><b>Problem:</b> Subproblems are recalculated repeatedly.
 * For example, {@code fib(5)} calls {@code fib(3)} twice, {@code fib(2)} three times, etc.
 * This creates an exponential explosion of redundant work.
 *
 * <p><b>Time Complexity: O(2^n)</b> — each call branches into two more calls,
 * forming a binary tree of depth n. The total number of calls grows exponentially.
 *
 * <p><b>Space Complexity: O(n)</b> — the call stack grows up to depth n
 * due to recursive calls before reaching the base case.
 */
public class FibonacciRecursion {

    /** Tracks how many times {@code fib()} is called, to illustrate redundant work. */
    private static int count = 0;

    static void main() {
        System.out.println(fib(5));
        System.out.println("************** Number of Times that a method has been called ****************");
        System.out.println(count);
    }

    /**
     * Computes the nth Fibonacci number using pure recursion.
     *
     * <p>No result is stored between calls. If the same subproblem appears
     * multiple times in the call tree, it is recomputed from scratch every time.
     *
     * @param n the position in the Fibonacci sequence (0-indexed)
     * @return the nth Fibonacci number
     */
    public static int fib(int n) {
        count++;

        // Base cases: fib(0) = 0, fib(1) = 1
        if (n == 0 || n == 1) {
            return n;
        }

        // Recursive case: fib(n) = fib(n-1) + fib(n-2)
        // WARNING: fib(n-1) and fib(n-2) are computed independently,
        // so overlapping subproblems are recalculated every time.
        return fib(n - 1) + fib(n - 2);
    }
}