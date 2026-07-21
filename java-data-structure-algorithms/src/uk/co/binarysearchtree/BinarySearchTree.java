package uk.co.binarysearchtree;


/**
 * Array Implementation
 */
public class BinarySearchTree {

    Node root;

    class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }


    public boolean insert(int value) {

        Node newNode = new Node(value);
        if (this.root == null) {
            this.root = newNode;
            return true;
        }

        Node temp = this.root;

        while (true) {

            if (temp.value == value) return false;

            if (value < temp.value) {
                if (temp.left == null) {
                    temp.left = newNode;
                    return true;
                } else {
                    temp = temp.left;
                }
            } else {
                if (temp.right == null) {
                    temp.right = newNode;
                    return true;
                } else {
                    temp = temp.right;
                }
            }
        }
    }


    public boolean contains(int value) {

        if (root == null) return false;

        Node temp = this.root;

        while (temp != null) {

            if (value < temp.value) {
                temp = temp.left;
            } else if (value > temp.value) {
                temp = temp.right;
            } else {
                return true;
            }
        }
        return false;
    }

}
