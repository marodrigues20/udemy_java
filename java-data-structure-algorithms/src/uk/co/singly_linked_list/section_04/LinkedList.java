package uk.co.singly_linked_list.section_04;

public class LinkedList {

    private Node head;
    private Node tail;
    private int length = 0;

    public LinkedList(int value) {
        this.head = new Node(value);
        this.tail = head;
        this.length++;
    }

    static class Node {
        private int value;
        private Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public Node get(int index) {

        Node temp = this.head;

        if (index < 0 || index >= this.length) return null;

        for (int i = 0; i < index; i++) {
            if (temp.next != null) {
                temp = temp.next;
            } else {
                return temp;
            }
        }

        return temp;
    }


    public void append(int value) {
        Node newNode = new Node(value);
        if (this.tail != null) {
            this.tail.next = newNode;
            this.tail = newNode;
        } else {
            this.head = newNode;
            this.tail = newNode;
        }
        this.length++;
    }

    public void prepend(int value) {

        Node newNode = new Node(value);
        if (this.head != null) {
            newNode.next = head;
            this.head = newNode;
        } else {
            this.head = newNode;
            this.tail = newNode;
        }
        this.length++;
    }

    public boolean insert(int index, int value) {

        if (index < 0 || index > this.length) return false;

        if (index == 0) {
            prepend(value);
            return true;
        }

        if (index == this.length) {
            this.append(value);
            return true;
        }

        Node prev = get(index - 1);
        Node newNode = new Node(value);
        newNode.next = prev.next;
        prev.next = newNode;
        this.length++;
        return true;
    }

    public void printList() {
        Node temp = this.head;
        while (temp != null) {
            System.out.println("Node value: " + temp.value);
            temp = temp.next;
        }
    }

    public boolean removeLast() {

        if (this.length == 0) {
            return false;
        }

        if (this.length == 1) {
            this.head = null;
            this.tail = null;
            this.length--;
            return true;
        }

        Node temp = this.get(this.length - 2);
        this.tail = temp;
        temp.next = null;
        this.length--;
        return true;
    }

    public boolean removeFirst() {

        if (length == 0) {
            return false;
        }

        if (this.length == 1) {
            this.head = null;
            this.tail = null;
            this.length--;
            return true;
        }

        Node temp = this.head.next;
        this.head = temp;
        this.length--;
        return true;
    }

    public boolean set(int index, int value) {

        if (index < 0 || index >= this.length) return false;

        this.get(index).value = value;
        return true;
    }

    public boolean remove(int index) {

        if (index < 0 || index >= this.length) return false;

        if (index == 0) {
            this.removeFirst();
            return true;
        }

        if (index == this.length - 1) {
            this.removeLast();
            return true;
        }

        Node temp = this.get(index - 1);
        temp.next = temp.next.next;
        length--;
        return true;
    }

    public boolean reverse() {

        if (this.length <= 1) return false;

        Node temp = this.head;
        this.head = this.tail;
        this.tail = temp;

        Node before = null;
        Node after = temp.next;

        for (int i = 0; i < this.length; i++) {
            temp.next = before;
            before = temp;
            temp = after;

            if(after.next != null)
                after = after.next;
        }

        return true;
    }

}