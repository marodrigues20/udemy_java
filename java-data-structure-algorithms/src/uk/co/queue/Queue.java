package uk.co.queue;

public class Queue {

    Node first;
    Node last;
    int length = 0;

    public Queue(int value) {
        Node newNode = new Node(value);
        this.first = newNode;
        this.last = newNode;
        this.length++;
    }


    public void enqueue(int value) {
        Node newNode = new Node(value);
        if(length == 0){
            this.first = newNode;
            this.last = newNode;
        }else{
            Node temp = this.last;
            temp.next = newNode;
            this.last = newNode;
        }

        this.length++;
    }


    public Node dequeue() {

        if (this.length == 0) return null;

        Node temp = this.first;

        if (this.length == 1) {
            this.first = null;
            this.last = null;
            temp.next = null;
        } else {
            this.first = this.first.next;
            temp.next = null;
        }

        this.length--;
        return temp;
    }


    public Node peek(){

        if (this.length == 0) {
            System.out.println("Queue is empty.");
            return null;
        }
        return this.first;
    }

    public void printQueue(){
        if(this.length == 0){
            System.out.println("Queue is empty.");
            return;
        }
        Node temp = this.first;
        while(temp != null){
            System.out.println("Item in the queue: " + temp.value);
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
