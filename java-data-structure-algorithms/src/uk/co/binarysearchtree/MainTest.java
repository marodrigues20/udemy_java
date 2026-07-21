package uk.co.binarysearchtree;

public class MainTest {

    public static void main(String[] args) {

        int passed = 0;
        int failed = 0;

        BinarySearchTree bst = new BinarySearchTree();

        // --- insert tests ---
        passed += check("insert(2)", bst.insert(2), true);
        passed += check("insert(1)", bst.insert(1), true);
        passed += check("insert(3)", bst.insert(3), true);
        passed += check("insert(2) duplicado", bst.insert(2), false);

        // --- contains tests ---
        passed += check("contains(2)", bst.contains(2), true);
        passed += check("contains(1)", bst.contains(1), true);
        passed += check("contains(3)", bst.contains(3), true);
        passed += check("contains(99)", bst.contains(99), false);

        // --- empty tree test ---
        BinarySearchTree emptyBst = new BinarySearchTree();
        passed += check("emptyBst.contains(5)", emptyBst.contains(5), false);

        failed = 9 - passed;

        System.out.println();
        System.out.println("Total: " + passed + " passou, " + failed + " falhou");
    }

    static int check(String label, boolean actual, boolean expected) {
        boolean ok = actual == expected;
        System.out.println((ok ? "PASS" : "FAIL") + " - " + label
                + " -> " + actual + " (esperado: " + expected + ")");
        return ok ? 1 : 0;
    }
}