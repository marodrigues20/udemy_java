package uk.co.recursion.binary_search_tree;

public class Main {

    static void main() {
        BinarySearchTree bst = new BinarySearchTree();

        // === INSERT ===
        int[] values = {20, 10, 30, 4, 15, 25, 35, 1};
        for (int v : values) bst.insert(v);
        System.out.println("Root value: " + bst.root.value); // expected 20

        // === DUPLICATE INSERT (should not change the tree) ===
        bst.insert(10);
        System.out.println("contains(10) after duplicate insert -> " + bst.contains(10) + " (expected true)");

        // === CONTAINS ===
        System.out.println("contains(15) -> " + bst.contains(15) + " (expected true)");
        System.out.println("contains(1)  -> " + bst.contains(1) + " (expected true)");
        System.out.println("contains(99) -> " + bst.contains(99) + " (expected false)");
        System.out.println("contains(0)  -> " + bst.contains(0) + " (expected false)");

        // === DELETE: leaf node ===
        bst.delete(1); // 1 is a leaf
        System.out.println("contains(1) after delete -> " + bst.contains(1) + " (expected false)");

        // === DELETE: node with one child ===
        BinarySearchTree bst2 = new BinarySearchTree();
        for (int v : new int[]{20, 10, 30, 5}) bst2.insert(v);
        bst2.delete(10); // 10 has only one child (5)
        System.out.println("Root.left after deleting one-child node 10 -> " + bst2.root.left.value + " (expected 5)");

        // === DELETE: node with two children (successor logic) ===
        bst.delete(20); // root has two children -> successor is min of right subtree (25)
        System.out.println("New root value after deleting 20 -> " + bst.root.value + " (expected 25)");

        // === DELETE: last remaining node (tree becomes empty) ===
        BinarySearchTree bst3 = new BinarySearchTree();
        bst3.insert(42);
        bst3.delete(42);
        System.out.println("Root after deleting only node -> " + bst3.root + " (expected null)");
        System.out.println("contains(42) on empty tree -> " + bst3.contains(42) + " (expected false, no NPE)");
    }
}