package uk.co.doubly_linkedlist;

public class DoublyLinkedList {

    private Node head;
    private Node tail;
    private int length;


    public DoublyLinkedList(int value) {
        Node newNode = new Node(value);
        this.head = newNode;
        this.tail = newNode;
        this.length = 1;
    }


    public void getHead() {
        System.out.println("Head: " + this.head.val);
    }

    public void getTail() {
        System.out.println("Tail: " + this.tail.val);
    }

    public void getLenght() {
        System.out.println("Lenght: " + this.length);
    }

    public void prinList() {
        Node after = this.head;
        while (after != null) {
            System.out.println("Node: " + after.val);
            after = after.next;
        }
    }

    public void append(int value) {
        Node newNode = new Node(value);

        if (this.length == 0) {
            this.head = newNode;
            this.tail = newNode;
        }

        if( this.length > 0){
            this.tail.next = newNode;
            newNode.prev = this.tail;
            this.tail = newNode;
        }

        this.length++;
    }


    public Node removeLast() {
        if (length == 0) return null;
        Node temp = tail;
        if (length == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
            temp.prev = null;
        }
        length--;
        return temp;
    }

    public void prepend(int value){

        Node newNode = new Node(value);

        if (this.length == 0){
            this.head = newNode;
            this.tail = newNode;
        }else{
            newNode.next = head;
            this.head.prev = newNode;
            this.head = newNode;
        }

        this.length++;
    }


    public Node removeFirst(){
        if(this.length == 0) return null;

        Node temp = this.head;

        if(this.length == 1){
            this.head = null;
            this.tail = null;
        }else{

            this.head = this.head.next;
            this.head.prev = null;
            temp.next = null;
        }

        this.length--;
        return temp;
    }
    
    
    public Node get(int index){

        if (index < 0 || index >= this.length) return null;

        int breakEvenPoint = this.length / 2;
        Node temp;
        if(index < breakEvenPoint){
            temp = this.head;
            for (int i = 0; i < index; i++ ){
                temp = temp.next;
            }
        }else{
            temp = this.tail;
            for (int i = this.length - 1; i > index; i-- ){
                temp = temp.prev;
            }
        }
        return temp;
    }


    public boolean set(int index, int value){
        Node node = get(index);
        if(node == null) return false;
        node.val = value;
        return true;
    }


    public boolean insert(int index, int value){

        if (index < 0 || index > this.length) return false;

        if ( index == 0){
            this.prepend(value);
            return true;
        }

        if ( index == this.length){
            this.append(value);
            return true;
        }

        Node newNode = new Node(value);
        Node before = get(index - 1);
        Node after = before.next;
        before.next = newNode;
        after.prev = newNode;
        newNode.next = after;
        newNode.prev = before;
        this.length++;
        return true;
    }


    public Node remove(int index){

        if (index < 0 || index >= this.length) return null;

        if(index == 0){
            return this.removeFirst();
        }

        if(index == this.length - 1){
            return this.removeLast();
        }

        Node temp = get(index);
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        temp.next = null;
        temp.prev = null;
        this.length--;
        return temp;

    }


    class Node {
        int val;
        Node next;
        Node prev;

        public Node(int val) {
            this.val = val;
        }
    }
}
