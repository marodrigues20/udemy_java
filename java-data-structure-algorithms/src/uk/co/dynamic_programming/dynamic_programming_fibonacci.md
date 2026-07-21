# Dynamic Programming — Fibonacci

## O que é Dynamic Programming?

DP é uma **estratégia**, não um código específico.

> **Regra fundamental:** "Se eu já resolvi esse subproblema antes, **não resolvo de novo** — reutilizo o resultado."

Qualquer código que siga essa regra **é DP**. A forma de implementar pode variar.

---

## Os 4 Approaches

| # | Nome | Direction | Recursion | Time | Space |
|---|------|-----------|-----------|------|-------|
| 1 | Pure Recursion | Top-Down | ✅ Sim | O(2ⁿ) | O(n) |
| 2 | Memoization | Top-Down | ✅ Sim | O(n) | O(n) |
| 3 | Iterative Tabulation | Bottom-Up | ❌ Não | O(n) | O(n) |
| 4 | Optimized Iterative | Bottom-Up | ❌ Não | O(n) | O(1) ✅ |

> ⚠️ **Apenas o approach #1 (Pure Recursion) NÃO é DP** — porque não armazena nem reutiliza resultados.
> Os approaches #2, #3 e #4 são todos DP.

---

## 1. Pure Recursion — NÃO é DP ❌

**Direction:** Top-Down
**Time:** O(2ⁿ) — exponencial
**Space:** O(n) — call stack

```java
/**
 * Pure Recursion — NOT Dynamic Programming.
 *
 * Each call to fib(n) branches into two recursive calls: fib(n-1) and fib(n-2).
 * Subproblems are recomputed repeatedly, causing exponential time complexity.
 *
 * Time Complexity:  O(2^n) — binary call tree of depth n
 * Space Complexity: O(n)   — maximum call stack depth
 */
public static int fib(int n) {
    if (n == 0 || n == 1) {
        return n;
    }
    return fib(n - 1) + fib(n - 2);
}
```

**Problema:** `fib(5)` recalcula `fib(3)` duas vezes, `fib(2)` três vezes, etc.
Para `fib(40)` são ~330 milhões de chamadas. 😬

---

## 2. Memoization (Top-Down DP) ✅

**Direction:** Top-Down
**Time:** O(n)
**Space:** O(n) — cache array + call stack

```java
/**
 * Memoization — Top-Down Dynamic Programming.
 *
 * Still recursive, but results are cached in an Integer[] array.
 * Each subproblem is solved only once; subsequent calls return the cached result immediately.
 *
 * Why Integer[] and not int[]?
 * int[] defaults to 0, which is a valid Fibonacci result (fib(0) = 0).
 * Integer[] defaults to null, which unambiguously means "not yet computed".
 *
 * Time Complexity:  O(n) — each unique n is computed exactly once
 * Space Complexity: O(n) — O(n) cache + O(n) call stack
 */
private static Integer[] cache = new Integer[100];

public static int fib(int n) {
    if (cache[n] != null) {       // Cache hit — return immediately
        return cache[n];
    }
    if (n == 0 || n == 1) {
        return n;
    }
    cache[n] = fib(n - 1) + fib(n - 2); // Compute once, store in cache
    return cache[n];
}
```

**Para `fib(40)`:** apenas 79 chamadas ao invés de ~330 milhões. ✅

---

## 3. Iterative Tabulation (Bottom-Up DP) ✅

**Direction:** Bottom-Up
**Time:** O(n)
**Space:** O(n) — array de tamanho n+1

```java
/**
 * Iterative Tabulation — Bottom-Up Dynamic Programming.
 *
 * Instead of starting from fib(n) and recursing downward (top-down),
 * this approach starts from the base cases fib(0) and fib(1)
 * and builds up the solution iteratively until reaching fib(n).
 *
 * "Tabulation" = filling a table (array) bottom-up.
 * No recursion, no call stack overhead.
 *
 * Optimisation opportunity: since each iteration only needs
 * the two previous values, the array can be replaced with two variables,
 * reducing Space Complexity to O(1).
 *
 * Time Complexity:  O(n) — loop runs n-1 times
 * Space Complexity: O(n) — array of size n+1
 */
public static int fib(int n) {
    int[] res = new int[n + 1];
    res[0] = 0; // Base case: fib(0) = 0
    res[1] = 1; // Base case: fib(1) = 1

    for (int index = 2; index <= n; index++) {
        res[index] = res[index - 1] + res[index - 2];
    }

    return res[n];
}
```

---

## 4. Optimized Iterative (Bottom-Up DP) ✅

**Direction:** Bottom-Up
**Time:** O(n)
**Space:** O(1) ← melhor possível

```java
/**
 * Optimized Iterative — Bottom-Up Dynamic Programming with constant space.
 *
 * The array of size n+1 is replaced by just two variables (prev and curr),
 * representing a sliding window of the two most recent Fibonacci values.
 * At each step, the oldest value is discarded and replaced by the newest.
 *
 * Time Complexity:  O(n) — loop runs from 3 to n
 * Space Complexity: O(1) — only three integer variables used
 */
public static int fib(int n) {
    if (n == 1 || n == 2) {
        return 1;
    }

    int prev   = 1;  // represents fib(i-2)
    int curr   = 1;  // represents fib(i-1)
    int result = 0;  // represents fib(i)

    for (int i = 3; i <= n; i++) {
        result = prev + curr; // fib(i) = fib(i-2) + fib(i-1)
        prev   = curr;        // slide window forward
        curr   = result;      // slide window forward
    }

    return result;
}
```

---

## Top-Down vs Bottom-Up — Resumo Visual

```
fib(5)
├── fib(4)                   ← Top-Down: começa do problema grande
│   ├── fib(3)               e vai quebrando em subproblemas menores
│   │   ├── fib(2)
│   │   └── fib(1)
│   └── fib(2)
└── fib(3)

fib(0) → fib(1) → fib(2) → fib(3) → fib(4) → fib(5)
                                                      ← Bottom-Up: começa dos casos base
                                                         e constrói até o resultado final
```

---

## Insight Final

> *"Toda vez que você usou um HashMap para cache, um array para guardar resultados
> intermediários, ou evitou recalcular algo que já tinha calculado —
> você estava aplicando o princípio de DP sem saber."* 🧠

DP no fundo é só **bom senso computacional**:
**"Já calculei isso antes — por que calcular de novo?"**
