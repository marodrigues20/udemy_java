package uk.co.doubly_linkedlist;

public class Main {

    static void main() {

        DoublyLinkedList myDLL = new DoublyLinkedList(0);
        myDLL.append(1);
        myDLL.append(2);

        myDLL.remove(1);

        myDLL.prinList();
    }
}
