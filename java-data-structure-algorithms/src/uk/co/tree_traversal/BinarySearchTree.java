package uk.co.tree_traversal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A Binary Search Tree (BST) implementation storing unique {@code int} values.
 * <p>
 * Invariant: for every node, all values in its left subtree are smaller than
 * the node's value, and all values in its right subtree are greater. Duplicate
 * values are not inserted.
 * <p>
 * <b>Complexity note:</b> every recursive method below runs in O(h) time,
 * where {@code h} is the height of the tree.
 * <ul>
 *     <li>Balanced tree: h = O(log n) &rarr; operations are O(log n)</li>
 *     <li>Degenerate/unbalanced tree (e.g. inserted in sorted order): h = O(n)
 *     &rarr; operations degrade to O(n)</li>
 * </ul>
 * This class does not self-balance (unlike AVL or Red-Black trees), so the
 * worst case always applies if input order is adversarial.
 */
public class BinarySearchTree {

    Node root;

    /**
     * A single node in the tree, holding a value and references to its
     * left and right children.
     */
    public class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * Recursively finds the correct position for {@code value} and inserts
     * a new node there. If {@code value} already exists, the tree is
     * returned unchanged (no duplicates allowed).
     *
     * @param currentNode the root of the subtree currently being examined
     * @param value       the value to insert
     * @return the (possibly new) root of the subtree, with the value inserted
     *
     * <p><b>Time complexity:</b> O(h) — one recursive call per level descended,
     * where h is the tree height (O(log n) balanced, O(n) worst case).
     * <p><b>Space complexity:</b> O(h) — call stack depth from recursion.
     */
    private Node rInsert(Node currentNode, int value) {
        if (currentNode == null) return new Node(value);
        if (value < currentNode.value) {
            currentNode.left = rInsert(currentNode.left, value);
        } else if (value > currentNode.value) {
            currentNode.right = rInsert(currentNode.right, value);
        }
        return currentNode;
    }

    /**
     * Inserts {@code value} into the tree, starting from the root.
     *
     * @param value the value to insert
     *
     *              <p><b>Time complexity:</b> O(h) — delegates directly to {@link #rInsert}.
     *              <p><b>Space complexity:</b> O(h) — recursive call stack.
     */
    public void insert(int value) {
        this.root = rInsert(this.root, value);
    }

    /**
     * Recursively searches for {@code value} starting at {@code currentNode}.
     *
     * @param currentNode the root of the subtree currently being examined
     * @param value       the value being searched for
     * @return {@code true} if found, {@code false} otherwise
     *
     * <p><b>Time complexity:</b> O(h) — at most one recursive call per level.
     * <p><b>Space complexity:</b> O(h) — recursive call stack.
     */
    private boolean rContains(Node currentNode, int value) {
        if (currentNode == null) return false;
        if (value < currentNode.value) {
            return rContains(currentNode.left, value);
        } else if (value > currentNode.value) {
            return rContains(currentNode.right, value);
        } else {
            return true;
        }
    }

    /**
     * Checks whether {@code value} exists in the tree.
     *
     * @param value the value to search for
     * @return {@code true} if present, {@code false} otherwise
     *
     * <p><b>Time complexity:</b> O(h) — delegates directly to {@link #rContains}.
     * <p><b>Space complexity:</b> O(h) — recursive call stack.
     */
    public boolean contains(int value) {
        return rContains(this.root, value);
    }

    /**
     * Finds the minimum value in the subtree rooted at {@code currentNode},
     * by walking left until no further left child exists. Used internally
     * to find the in-order successor when deleting a node with two children.
     *
     * @param currentNode the root of the subtree to search (must not be null)
     * @return the smallest value in that subtree
     *
     * <p><b>Time complexity:</b> O(h) — iterative, one step per level walked left.
     * <p><b>Space complexity:</b> O(1) — iterative, no extra stack frames.
     */
    private int minValue(Node currentNode) {
        while (currentNode.left != null) {
            currentNode = currentNode.left;
        }
        return currentNode.value;
    }

    /**
     * Recursively removes {@code value} from the subtree rooted at
     * {@code currentNode}, handling all three deletion cases:
     * <ol>
     *     <li>Leaf node — simply removed (returns {@code null})</li>
     *     <li>One child — the node is replaced by its single child</li>
     *     <li>Two children — replaced by its in-order successor (the minimum
     *     value of the right subtree), then that duplicate is removed from
     *     the right subtree</li>
     * </ol>
     *
     * @param currentNode the root of the subtree currently being examined
     * @param value       the value to remove
     * @return the (possibly new) root of the subtree, with the value removed
     *
     * <p><b>Time complexity:</b> O(h) for the search to locate the node, plus
     * O(h) for {@link #minValue} in the two-children case &mdash; still O(h)
     * overall, since both walk at most the height of the tree.
     * <p><b>Space complexity:</b> O(h) — recursive call stack.
     */
    private Node deleteNode(Node currentNode, int value) {
        if (currentNode == null) return null;

        if (value < currentNode.value) {
            currentNode.left = deleteNode(currentNode.left, value);
        } else if (value > currentNode.value) {
            currentNode.right = deleteNode(currentNode.right, value);
        } else {
            if (currentNode.left == null && currentNode.right == null) {
                return null;
            } else if (currentNode.left != null && currentNode.right == null) {
                return currentNode.left;
            } else if (currentNode.left == null && currentNode.right != null) {
                return currentNode.right;
            } else {
                int subTreeMin = minValue(currentNode.right);
                currentNode.value = subTreeMin;
                currentNode.right = deleteNode(currentNode.right, subTreeMin);
            }
        }
        return currentNode;
    }

    /**
     * Removes {@code value} from the tree, starting from the root.
     * If {@code value} is not present, the tree is left unchanged.
     *
     * @param value the value to remove
     *
     *              <p><b>Time complexity:</b> O(h) — delegates directly to {@link #deleteNode}.
     *              <p><b>Space complexity:</b> O(h) — recursive call stack.
     */
    public void delete(int value) {
        this.root = deleteNode(this.root, value);
    }

    /**
     * Traverses the tree level by level (Breadth-First Search), starting
     * from the root, using a queue to visit each node's children before
     * moving to the next depth level.
     *
     * @return an {@link ArrayList} of values in level-order; empty if the
     * tree has no root
     *
     * <p><b>Time complexity:</b> O(n) — every node is enqueued and dequeued
     * exactly once.
     * <p><b>Space complexity:</b> O(n) — worst case the queue holds an
     * entire tree level at once (up to n/2 nodes for a balanced tree), plus
     * the O(n) result list.
     */
    public ArrayList<Integer> BFS() {

        ArrayList<Integer> result = new ArrayList<>();

        if (this.root == null) return result;

        Node currentNode = this.root;
        Queue<Node> queue = new LinkedList<>();
        queue.add(currentNode);

        while (queue.size() > 0) {

            currentNode = queue.poll();
            result.add(currentNode.value);

            if (currentNode.left != null) {
                queue.add(currentNode.left);
            }

            if (currentNode.right != null) {
                queue.add(currentNode.right);
            }


        }

        return result;
    }

    /**
     * Traverses the tree using Depth-First Search in pre-order
     * (node &rarr; left &rarr; right), starting from the root.
     *
     * @return an {@link ArrayList} of values in pre-order; empty if the
     * tree has no root
     *
     * <p><b>Time complexity:</b> O(n) — every node is visited exactly once.
     * <p><b>Space complexity:</b> O(h) — recursive call stack depth (O(n)
     * result list not counted), where h is the tree height.
     */
    public ArrayList<Integer> DFSPreOrder() {
        ArrayList<Integer> results = new ArrayList<>();
        if (this.root == null) return results;
        class Traverse {
            Traverse(Node currentNode) {
                results.add(currentNode.value);
                if (currentNode.left != null) {
                    new Traverse(currentNode.left);
                }
                if (currentNode.right != null) {
                    new Traverse(currentNode.right);
                }
            }
        }
        new Traverse(this.root);
        return results;
    }

    /**
     * Traverses the tree using Depth-First Search in post-order
     * (left &rarr; right &rarr; node), starting from the root.
     *
     * @return an {@link ArrayList} of values in post-order; empty if the
     * tree has no root
     *
     * <p><b>Time complexity:</b> O(n) — every node is visited exactly once.
     * <p><b>Space complexity:</b> O(h) — recursive call stack depth (O(n)
     * result list not counted), where h is the tree height.
     */
    public ArrayList<Integer> DFSPostOrder() {
        ArrayList<Integer> result = new ArrayList<>();
        if (this.root == null) return result;
        class Traverse {
            Traverse(Node currentNode) {
                if (currentNode.left != null) {
                    new Traverse(currentNode.left);
                }
                if (currentNode.right != null) {
                    new Traverse(currentNode.right);
                }
                result.add(currentNode.value);
            }
        }

        new Traverse(this.root);
        return result;
    }

    /**
     * Traverses the tree using Depth-First Search in in-order
     * (left &rarr; node &rarr; right), starting from the root. For a valid
     * BST this produces values in ascending sorted order.
     *
     * @return an {@link ArrayList} of values in ascending sorted order;
     * empty if the tree has no root
     *
     * <p><b>Time complexity:</b> O(n) — every node is visited exactly once.
     * <p><b>Space complexity:</b> O(h) — recursive call stack depth (O(n)
     * result list not counted), where h is the tree height.
     */
    public ArrayList<Integer> DFSInOrder() {
        ArrayList<Integer> result = new ArrayList<>();
        if (this.root == null) return result;
        class Traverse {
            Traverse(Node currentNode) {
                if (currentNode.left != null) {
                    new Traverse(currentNode.left);
                }
                result.add(currentNode.value);
                if (currentNode.right != null) {
                    new Traverse(currentNode.right);
                }
            }
        }
        new Traverse(this.root);
        return result;
    }
}