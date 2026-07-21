package uk.co.dynamic_programming;

/**
 * Demonstrates the Dynamic Programming (Memoization) approach to computing Fibonacci numbers.
 *
 * <p><b>Approach:</b> Top-Down with Memoization.
 * This is still a recursive (top-down) approach, but results are cached in an array
 * so that each subproblem is solved only once. On subsequent calls with the same {@code n},
 * the cached result is returned immediately without any further recursion.
 *
 * <p><b>Key difference from pure recursion:</b> Instead of recomputing {@code fib(k)}
 * every time it appears in the call tree, the result is stored in {@code cache[k]}
 * after the first computation and reused for all future calls.
 *
 * <p><b>Why {@code Integer[]} and not {@code int[]}?</b>
 * Java initialises {@code int[]} arrays with {@code 0} by default.
 * Since {@code fib(0) = 0} is a valid result, we cannot distinguish
 * "not yet computed" from "computed and equals zero" using a primitive array.
 * {@code Integer[]} defaults to {@code null}, which unambiguously means "not yet computed".
 *
 * <p><b>Time Complexity: O(n)</b> — each unique value of {@code n} is computed exactly once.
 * The cache eliminates all redundant recursive calls.
 *
 * <p><b>Space Complexity: O(n)</b> — O(n) for the cache array
 * plus O(n) for the call stack depth, giving O(n) overall.
 */
public class FibonacciDynamicProgramming {

    /**
     * Cache storing previously computed Fibonacci results.
     * {@code cache[n]} holds the result of {@code fib(n)}, or {@code null} if not yet computed.
     * Uses {@code Integer} (not {@code int}) so that {@code null} can represent "uncached".
     */
    private static Integer[] cache = new Integer[100];

    /** Tracks how many times {@code fib()} is called, to illustrate the savings from memoization. */
    private static int count = 0;

    static void main() {
        System.out.println(fib(40));
        System.out.println("*************** Number of Times that Method is called *************");
        System.out.println(count);
    }

    /**
     * Computes the nth Fibonacci number using top-down Dynamic Programming (memoization).
     *
     * <p>Before computing, checks whether the result for {@code n} is already cached.
     * If so, returns it immediately (O(1)). Otherwise, computes it recursively,
     * stores it in the cache, and returns it.
     *
     * @param n the position in the Fibonacci sequence (0-indexed); must be &lt; 100
     * @return the nth Fibonacci number
     */
    public static int fib(int n) {
        count++;

        // Cache hit: result already computed — return immediately, no further recursion
        if (cache[n] != null) {
            return cache[n];
        }

        // Base cases: fib(0) = 0, fib(1) = 1
        if (n == 0 || n == 1) {
            return n;
        }

        // Recursive case: compute, store in cache, then return
        // Each unique n is computed only once — all subsequent calls hit the cache above
        cache[n] = fib(n - 1) + fib(n - 2);

        return cache[n];
    }
}