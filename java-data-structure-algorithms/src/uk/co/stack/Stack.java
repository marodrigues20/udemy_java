package uk.co.stack;

public class Stack {

    private Node top;
    private int length = 0;


    public Stack(int value) {
        Node newNode = new Node(value);
        this.top = newNode;
        this.length++;
    }

    public void push(int value) {
        Node newNode = new Node(value);
        Node temp = this.top;
        this.top = newNode;
        this.top.next = temp;
        this.length++;
    }

    public Node pop() {

        if (this.length == 0) return null;
        Node temp = this.top;
        this.top = this.top.next;
        temp.next = null;
        this.length--;
        return temp;
    }

    public Node peek() {
        if (this.length == 0) return null;
        return top;
    }

    public void printStack() {

        if (this.top == null) {
            System.out.println("Stack is empty");
            return;
        }

        Node temp = this.top;
        while (temp != null) {
            System.out.println(temp.value);
            temp = temp.next;
        }
    }

    class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }
}
